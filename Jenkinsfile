pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'your-registry'
        KUBE_CONFIG = credentials('kubeconfig')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'docker build -t ${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER} .'
                }
            }
        }

        stage('Build AI Service') {
            steps {
                dir('ai-service') {
                    sh 'docker build -t ${DOCKER_REGISTRY}/ai-service:${BUILD_NUMBER} .'
                }
            }
        }

        stage('Run Tests') {
            parallel {
                stage('Frontend Tests') {
                    steps {
                        dir('frontend') {
                            sh 'npm ci'
                            sh 'npm test'
                        }
                    }
                }
                stage('Backend Tests') {
                    steps {
                        dir('ai-service') {
                            sh 'pip install -r requirements.txt'
                            sh 'python -m pytest'
                        }
                    }
                }
            }
        }

        stage('Security Scan') {
            steps {
                sh 'trivy image ${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER}'
                sh 'trivy image ${DOCKER_REGISTRY}/ai-service:${BUILD_NUMBER}'
            }
        }

        stage('Push Images') {
            steps {
                sh 'docker push ${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER}'
                sh 'docker push ${DOCKER_REGISTRY}/ai-service:${BUILD_NUMBER}'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl --kubeconfig $KUBE_CONFIG apply -f k8s/'
                sh "kubectl --kubeconfig $KUBE_CONFIG set image deployment/frontend frontend=${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER}"
                sh "kubectl --kubeconfig $KUBE_CONFIG set image deployment/ai-service ai-service=${DOCKER_REGISTRY}/ai-service:${BUILD_NUMBER}"
            }
        }

        stage('Monitor Deployment') {
            steps {
                sh 'kubectl --kubeconfig $KUBE_CONFIG rollout status deployment/frontend'
                sh 'kubectl --kubeconfig $KUBE_CONFIG rollout status deployment/ai-service'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}