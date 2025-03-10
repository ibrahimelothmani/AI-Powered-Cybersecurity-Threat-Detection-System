import os
import json
import asyncio
import websockets
from kafka import KafkaConsumer
from loguru import logger
from dotenv import load_dotenv

load_dotenv()

class WebSocketServer:
    def __init__(self):
        self.consumer = KafkaConsumer(
            os.getenv('KAFKA_TOPIC', 'network-traffic'),
            bootstrap_servers=os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092'),
            auto_offset_reset='latest',
            enable_auto_commit=True,
            group_id='websocket-server',
            value_deserializer=lambda x: json.loads(x.decode('utf-8'))
        )
        self.connected_clients = set()
        logger.info("WebSocket server initialized and connected to Kafka")

    async def register(self, websocket):
        self.connected_clients.add(websocket)
        logger.info(f"Client connected. Total clients: {len(self.connected_clients)}")

    async def unregister(self, websocket):
        self.connected_clients.remove(websocket)
        logger.info(f"Client disconnected. Total clients: {len(self.connected_clients)}")

    async def send_updates(self):
        try:
            for message in self.consumer:
                if not self.connected_clients:
                    await asyncio.sleep(1)
                    continue

                data = message.value
                websockets_tasks = []
                for websocket in self.connected_clients:
                    try:
                        task = asyncio.create_task(
                            websocket.send(json.dumps(data))
                        )
                        websockets_tasks.append(task)
                    except websockets.exceptions.ConnectionClosed:
                        await self.unregister(websocket)

                if websockets_tasks:
                    await asyncio.gather(*websockets_tasks)

        except Exception as e:
            logger.error(f"Error in send_updates: {str(e)}")

    async def handler(self, websocket, path):
        await self.register(websocket)
        try:
            await websocket.wait_closed()
        finally:
            await self.unregister(websocket)

    async def start(self):
        server = await websockets.serve(
            self.handler,
            os.getenv('WEBSOCKET_HOST', 'localhost'),
            int(os.getenv('WEBSOCKET_PORT', 8765))
        )
        logger.info(f"WebSocket server started on {server.sockets[0].getsockname()}")
        await asyncio.gather(
            server.serve_forever(),
            self.send_updates()
        )

if __name__ == '__main__':
    server = WebSocketServer()
    asyncio.run(server.start())