pipeline {

    agent any

    environment {

        AWS_REGION = "eu-north-1"
        EKS_CLUSTER = "wonderful-rock-mountain"

    }

    stages {

        stage('Checkout') {

            steps {

                git branch: 'main',
                    url: 'https://github.com/Sujay9939/Food-delevery-Website'
            }
        }

        stage('Build Maven') {

            steps {

                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {

            steps {

                sh '''
                docker build \
                -t food-delivery-app:v1 .
                '''
            }
        }

        stage('Deploy To EKS') {

            steps {

                withAWS(
                    credentials: 'aws-creds',
                    region: 'eu-north-1'
                ) {

                    sh '''
                    aws eks update-kubeconfig \
                    --region eu-north-1 \
                    --name wonderful-rock-mountain

                    kubectl get nodes

                    kubectl apply -f k8s/

                    kubectl rollout status \
                    deployment/food-delivery \
                    --timeout=300s
                    '''
                }
            }
        }
    }

    post {

        success {

            echo 'Application deployed successfully'
        }

        failure {

            echo 'Deployment failed'
        }

        always {

            cleanWs()
        }
    }
}
