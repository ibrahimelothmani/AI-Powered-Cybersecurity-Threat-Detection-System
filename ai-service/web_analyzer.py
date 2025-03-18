import dns.resolver
import ssl
import socket
import whois
import requests
from datetime import datetime
from typing import Dict, List, Any

class WebAnalyzer:
    def __init__(self):
        self.dns_resolver = dns.resolver.Resolver()

    def analyze_dns(self, domain: str) -> List[Dict[str, str]]:
        records = []
        record_types = ['A', 'AAAA', 'MX', 'NS', 'TXT']
        
        for record_type in record_types:
            try:
                answers = self.dns_resolver.resolve(domain, record_type)
                for rdata in answers:
                    records.append({
                        'type': record_type,
                        'value': str(rdata)
                    })
            except Exception:
                continue
        
        return records

    def analyze_ssl(self, domain: str) -> Dict[str, Any]:
        try:
            context = ssl.create_default_context()
            with socket.create_connection((domain, 443)) as sock:
                with context.wrap_socket(sock, server_hostname=domain) as ssock:
                    cert = ssock.getpeercert()
                    return {
                        'valid': True,
                        'issuer': dict(x[0] for x in cert['issuer'])['commonName'],
                        'expiryDate': cert['notAfter']
                    }
        except Exception:
            return {
                'valid': False,
                'issuer': 'Unknown',
                'expiryDate': 'Unknown'
            }

    def analyze_headers(self, url: str) -> Dict[str, Dict[str, str]]:
        try:
            response = requests.head(f'https://{url}', allow_redirects=True)
            security_headers = {
                'Strict-Transport-Security': response.headers.get('Strict-Transport-Security', 'Not Set'),
                'Content-Security-Policy': response.headers.get('Content-Security-Policy', 'Not Set'),
                'X-Frame-Options': response.headers.get('X-Frame-Options', 'Not Set'),
                'X-Content-Type-Options': response.headers.get('X-Content-Type-Options', 'Not Set'),
                'X-XSS-Protection': response.headers.get('X-XSS-Protection', 'Not Set')
            }
            return {'security': security_headers}
        except Exception:
            return {'security': {}}

    def analyze_domain(self, domain: str) -> Dict[str, str]:
        try:
            domain_info = whois.whois(domain)
            return {
                'registrar': domain_info.registrar or 'Unknown',
                'creationDate': str(domain_info.creation_date[0] if isinstance(domain_info.creation_date, list) else domain_info.creation_date),
                'expiryDate': str(domain_info.expiration_date[0] if isinstance(domain_info.expiration_date, list) else domain_info.expiration_date)
            }
        except Exception:
            return {
                'registrar': 'Unknown',
                'creationDate': 'Unknown',
                'expiryDate': 'Unknown'
            }

    def analyze_website(self, url: str) -> Dict[str, Any]:
        try:
            # Validate URL format
            if not url.startswith(('http://', 'https://')):
                raise ValueError('Invalid URL format. URL must start with http:// or https://')

            # Clean the URL to get the domain
            domain = url.replace('https://', '').replace('http://', '').split('/')[0]
            
            # Validate domain format
            if not domain or '.' not in domain:
                raise ValueError('Invalid domain format')

            result = {
                'dns': {'records': self.analyze_dns(domain)},
                'ssl': self.analyze_ssl(domain),
                'headers': self.analyze_headers(domain),
                'domain': self.analyze_domain(domain)
            }

            # Validate that we got some meaningful results
            if not any(result.values()):
                raise Exception('Failed to retrieve any website information')

            return result

        except ValueError as e:
            raise ValueError(f'URL validation error: {str(e)}')
        except requests.exceptions.RequestException as e:
            raise Exception(f'Connection error: {str(e)}')
        except Exception as e:
            raise Exception(f'Analysis failed: {str(e)}')