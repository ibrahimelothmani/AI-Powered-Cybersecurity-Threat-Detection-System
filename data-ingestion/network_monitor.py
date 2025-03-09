import os
from typing import Dict, Any
from kafka import KafkaProducer
from scapy.all import sniff
from loguru import logger
from dotenv import load_dotenv
import traceback

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
            'source_ip': None,
            'dest_ip': None,
            'protocol': None,
            'length': len(packet),
            'source_port': None,
            'dest_port': None
        }
        
        # Check for IP layer and extract details
        if packet.haslayer('IP'):
            packet_info['source_ip'] = packet['IP'].src
            packet_info['dest_ip'] = packet['IP'].dst
            packet_info['protocol'] = packet['IP'].proto
        
        # Check for TCP/UDP layers and extract port details
        if packet.haslayer('TCP'):
            packet_info['source_port'] = packet['TCP'].sport
            packet_info['dest_port'] = packet['TCP'].dport
        elif packet.haslayer('UDP'):
            packet_info['source_port'] = packet['UDP'].sport
            packet_info['dest_port'] = packet['UDP'].dport

        try:
            self.kafka_producer.send(self.topic, packet_info)
            logger.debug(f"Sent packet info to Kafka: {packet_info}")
        except Exception as e:
            logger.error(f"Failed to send packet to Kafka: {str(e)}")

    def start_monitoring(self) -> None:
        """Start capturing network traffic"""
        logger.info("Starting network traffic monitoring...")
        try:
            sniff(count=5, prn=self.packet_callback, store=0)  # Capture only 5 packets for testing
        except KeyboardInterrupt:
            logger.info("Stopping network traffic monitoring...")
        except Exception as e:
            logger.error(f"Error in network monitoring: {str(e)}")
            logger.error(traceback.format_exc())  # Log the full traceback for better error diagnosis
        finally:
            self.kafka_producer.close()

if __name__ == '__main__':
    monitor = NetworkMonitor()
    monitor.start_monitoring()
