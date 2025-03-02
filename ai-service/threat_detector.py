import os
import numpy as np
from typing import List, Dict, Any
from kafka import KafkaConsumer
from tensorflow import keras
from sklearn.preprocessing import StandardScaler
from loguru import logger
from dotenv import load_dotenv

load_dotenv()

class ThreatDetector:
    def __init__(self):
        self.consumer = KafkaConsumer(
            os.getenv('KAFKA_TOPIC', 'network-traffic'),
            bootstrap_servers=os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092'),
            auto_offset_reset='latest',
            enable_auto_commit=True,
            group_id='threat-detector'
        )
        self.scaler = StandardScaler()
        self.model = self._build_model()
        logger.info("Threat detector initialized and connected to Kafka")

    def _build_model(self) -> keras.Model:
        """Build and compile the neural network model"""
        model = keras.Sequential([
            keras.layers.Dense(64, activation='relu', input_shape=(7,)),
            keras.layers.Dropout(0.2),
            keras.layers.Dense(32, activation='relu'),
            keras.layers.Dropout(0.2),
            keras.layers.Dense(16, activation='relu'),
            keras.layers.Dense(1, activation='sigmoid')
        ])
        
        model.compile(
            optimizer='adam',
            loss='binary_crossentropy',
            metrics=['accuracy']
        )
        return model

    def _preprocess_packet(self, packet_info: Dict[str, Any]) -> np.ndarray:
        """Convert packet info into model input format"""
        features = [
            float(packet_info.get('length', 0)),
            float(packet_info.get('source_port', 0)),
            float(packet_info.get('dest_port', 0)),
            float(packet_info.get('protocol', 0)),
            # Add more features as needed
        ]
        return np.array(features).reshape(1, -1)

    def detect_threats(self) -> None:
        """Process network traffic and detect threats"""
        logger.info("Starting threat detection...")
        try:
            for message in self.consumer:
                packet_info = eval(message.value.decode('utf-8'))
                features = self._preprocess_packet(packet_info)
                
                # Normalize features
                features_scaled = self.scaler.fit_transform(features)
                
                # Predict threat probability
                threat_prob = self.model.predict(features_scaled)[0][0]
                
                if threat_prob > 0.8:  # Threshold for high-risk threats
                    logger.warning(f"High-risk threat detected! Probability: {threat_prob:.2f}")
                    logger.warning(f"Packet info: {packet_info}")
                elif threat_prob > 0.5:  # Threshold for medium-risk threats
                    logger.info(f"Potential threat detected. Probability: {threat_prob:.2f}")
                    logger.info(f"Packet info: {packet_info}")

        except KeyboardInterrupt:
            logger.info("Stopping threat detection...")
        except Exception as e:
            logger.error(f"Error in threat detection: {str(e)}")
        finally:
            self.consumer.close()

if __name__ == '__main__':
    detector = ThreatDetector()
    detector.detect_threats()