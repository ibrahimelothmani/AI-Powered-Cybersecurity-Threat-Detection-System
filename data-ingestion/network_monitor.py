import os
from typing import Dict, Any
from kafka import KafkaProducer
from scapy.all import sniff
from loguru import logger
from dotenv import load_dotenv

load_dotenv()

class NetworkMonitor:
    def __init__(self):
        self.kafka_producer = KafkaProducer(
            bootstrap_servers=os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092'),
            value_serializer=lambda x: str(x).encode('utf-8')
        )
        self.topic = os.getenv('KAFKA_TOPIC', 'network-traffic')
        logger.info("Network monitor initialized and connected to Kafka")

    def packet_callback(self, packet) -> None:
        """Process captured packets and send to Kafka"""
        packet_info: Dict[str, Any] = {
            'timestamp': packet.time,
            'source_ip': packet.get('IP', {}).src if packet.haslayer('IP') else None,
            'dest_ip': packet.get('IP', {}).dst if packet.haslayer('IP') else None,
            'protocol': packet.get('IP', {}).proto if packet.haslayer('IP') else None,
            'length': len(packet),
            'source_port': packet.sport if hasattr(packet, 'sport') else None,
            'dest_port': packet.dport if hasattr(packet, 'dport') else None
        }

        try:
            self.kafka_producer.send(self.topic, packet_info)
            logger.debug(f"Sent packet info to Kafka: {packet_info}")
        except Exception as e:
            logger.error(f"Failed to send packet to Kafka: {str(e)}")

    def start_monitoring(self) -> None:
        """Start capturing network traffic"""
        logger.info("Starting network traffic monitoring...")
        try:
            sniff(prn=self.packet_callback, store=0)
        except KeyboardInterrupt:
            logger.info("Stopping network traffic monitoring...")
        except Exception as e:
            logger.error(f"Error in network monitoring: {str(e)}")
        finally:
            self.kafka_producer.close()

if __name__ == '__main__':
    monitor = NetworkMonitor()
    monitor.start_monitoring()