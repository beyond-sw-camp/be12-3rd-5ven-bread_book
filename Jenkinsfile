pipeline {
    agent none

    environment {
        IMAGE_NAME = 'wkdlrn/breadbookback'
        IMAGE_TAG  = "${BUILD_NUMBER}"
    }

    stages {
        stage('Build & Push') {
            agent { label 'build' }
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew bootJar'
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                sh "docker login -u wkdlrn -p qwer1234qwer"
                sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }

        stage('Prepare DB URL') {
            agent { label 'deploy' }
            steps {
                sh '''
                   mkdir -p ~/.ssh
                   ssh-keyscan -H 192.0.5.9 >> ~/.ssh/known_hosts
                '''
                script {
                    def svc = sh(
                        script: "ssh -o StrictHostKeyChecking=no test@192.0.5.9 \"export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl get svc db-svc -n breadbook -o jsonpath='{.spec.clusterIP}:{.spec.ports[0].port}'\"",
                        returnStdout: true
                    ).trim()
                    env.DB_URL = "jdbc:mariadb://${svc}/breadbook?useSSL=false"
                    echo "▶ DB_URL = ${env.DB_URL}"
                }
            }
        }

        stage('Blue-Green Deploy') {
            agent { label 'deploy' }
            steps {
                script {
                    def color      = (BUILD_NUMBER.toInteger() % 2 == 0) ? 'green' : 'blue'
                    def otherColor = (color == 'green') ? 'blue' : 'green'

                    sh """
ssh test@192.0.5.9 "export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  namespace: breadbook
  name: backend-deployment-${color}
spec:
  replicas: 2
  selector:
    matchLabels:
      type: backend
      deployment: ${color}
  template:
    metadata:
      labels:
        type: backend
        deployment: ${color}
    spec:
      containers:
      - name: backend-${color}
        image: ${IMAGE_NAME}:${IMAGE_TAG}
        env:
        - name: DB_URL
          value: "${DB_URL}"
        - name: MARIADB_DATABASE
          valueFrom:
            configMapKeyRef:
              name: db-cm
              key: MARIADB_DATABASE
        - name: MARIADB_ROOT_PASSWORD
          valueFrom:
            configMapKeyRef:
              name: db-cm
              key: MARIADB_ROOT_PASSWORD
EOF"
"""
                    sh "ssh test@192.0.5.9 \"kubectl rollout status deployment/backend-deployment-${color} -n breadbook --timeout=120s\""
                    sh "ssh test@192.0.5.9 \"kubectl patch svc backend-svc -n breadbook -p '{\\\"spec\\\":{\\\"selector\\\":{\\\"deployment\\\":\\\"${color}\\\"}}}'\""
                    sh "ssh test@192.0.5.9 \"kubectl scale deployment/backend-deployment-${otherColor} -n breadbook --replicas=0 || true\""
                }
            }
        }
    }
}
