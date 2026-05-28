pipeline {

agent any

environment {

    AWS_REGION = "eu-north-1"
    EKS_CLUSTER = "attractive-funk-dolphin"

    IMAGE_NAME = "sujaygope9939/food-delivery-app"
    IMAGE_TAG = "v1"
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
            -t $IMAGE_NAME:$IMAGE_TAG .
            '''
        }
    }

    stage('Docker Login & Push') {

        steps {

            withCredentials([usernamePassword(
                credentialsId: 'docker-hub-creds',
                usernameVariable: 'DOCKER_USER',
                passwordVariable: 'DOCKER_PASS'
            )]) {

                sh '''
                echo "$DOCKER_PASS" | docker login \
                -u "$DOCKER_USER" \
                --password-stdin

                docker push $IMAGE_NAME:$IMAGE_TAG
                '''
            }
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
                --region $AWS_REGION \
                --name $EKS_CLUSTER

                kubectl get nodes

                kubectl set image deployment/food-delivery \
                food-delivery=$IMAGE_NAME:$IMAGE_TAG

                kubectl apply -f k8s/

                kubectl rollout status deployment/food-delivery \
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
