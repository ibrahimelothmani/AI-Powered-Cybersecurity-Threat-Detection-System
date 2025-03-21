# AI-Powered Cybersecurity Threat Detection System

A comprehensive cybersecurity solution that leverages artificial intelligence to detect and mitigate real-time security threats through network traffic and system log analysis.

## Architecture Overview

### Components

1. **Frontend (React + TypeScript)**
   - Modern Material-UI dashboard
   - Real-time threat visualization
   - Responsive design for all devices
   - JWT-based authentication

2. **AI Service (Python + FastAPI)**
   - TensorFlow-based threat detection
   - Real-time URL and domain analysis
   - SSL certificate validation
   - Security headers inspection

3. **DevOps Infrastructure**
   - Containerization with Docker
   - Kubernetes orchestration
   - GitHub Actions CI/CD pipeline
   - Infrastructure as Code with Terraform
   - Configuration management with Ansible

4. **Monitoring Stack**
   - Prometheus metrics collection
   - Grafana dashboards
   - Kubernetes health monitoring
   - Application performance metrics

## Project Structure
```
.
├── Frontend/                 # React frontend application
│   ├── src/                 # Source code
│   ├── Dockerfile          # Frontend container configuration
│   └── package.json        # Dependencies and scripts
├── ai-service/              # Python AI service
│   ├── app.py             # FastAPI application
│   ├── web_analyzer.py    # Threat detection logic
│   ├── Dockerfile         # AI service container configuration
│   └── requirements.txt   # Python dependencies
├── k8s/                    # Kubernetes manifests
│   ├── frontend-deployment.yaml
│   └── ai-service-deployment.yaml
├── terraform/              # Infrastructure as Code
│   └── main.tf            # Terraform configuration
├── monitoring/             # Monitoring configuration
│   └── prometheus/        # Prometheus setup
├── ansible/               # Configuration management
│   └── site.yml          # Ansible playbook
└── .github/
    └── workflows/         # GitHub Actions CI/CD
```

## Prerequisites

- Docker Desktop with Kubernetes enabled
- Terraform >= 1.0.0
- Ansible >= 2.9
- Node.js >= 18.x
- Python >= 3.9
- AWS CLI configured (for cloud deployment)

## Quick Start

1. **Local Development**
   ```bash
   # Frontend
   cd Frontend
   npm install
   npm run dev

   # AI Service
   cd ai-service
   python -m venv venv
   source venv/bin/activate  # or .\venv\Scripts\activate on Windows
   pip install -r requirements.txt
   uvicorn app:app --reload
   ```

2. **Docker Deployment**
   ```bash
   # Build images
   docker build -t cybersecurity-frontend Frontend/
   docker build -t cybersecurity-ai ai-service/

   # Run containers
   docker-compose up -d
   ```

3. **Kubernetes Deployment**
   ```bash
   # Apply Kubernetes manifests
   kubectl apply -f k8s/

   # Check deployment status
   kubectl get pods -n cybersecurity
   ```

4. **Infrastructure Deployment**
   ```bash
   # Initialize and apply Terraform
   cd terraform
   terraform init
   terraform apply

   # Run Ansible playbook
   cd ../ansible
   ansible-playbook site.yml
   ```

## Monitoring

- Prometheus: Access metrics at `http://localhost:9090`
- Grafana: Access dashboards at `http://localhost:3000`
  - Default credentials: admin/admin
  - Pre-configured dashboards for system and application metrics

## CI/CD Pipeline

The GitHub Actions pipeline automatically:
1. Runs tests for both frontend and AI service
2. Builds and pushes Docker images
3. Deploys to Kubernetes cluster
4. Updates infrastructure using Terraform

## Security Notes

- All sensitive data should be stored in Kubernetes secrets
- Update the `.env` files with proper credentials
- Configure proper CORS settings in production
- Enable network policies in Kubernetes
- Regular security audits recommended

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

MIT License - See LICENSE file for details