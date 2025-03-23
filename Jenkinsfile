pipeline {
    agent none  // 전체 파이프라인에서는 기본 에이전트를 사용하지 않음

    environment {
        IMAGE_NAME = 'wkdlrn/breadbookback'      // 도커 허브에 푸시할 이미지 이름
        IMAGE_TAG = "${BUILD_NUMBER}"            // Jenkins의 빌드 번호를 태그로 사용
    }

    stages {
        /*
         * 🔧 [BUILD STAGE]
         * - Git 클론, Gradle 빌드, Docker 이미지 빌드 및 푸시
         * - 사용 가능한 기본 노드(Built-In Node 등)에서 실행됨
         */
        stage('Build & Push') {
            agent any
            steps {
                echo "✅ Gradle 실행 권한 부여"
                sh 'chmod +x gradlew'

                echo "✅ Gradle Build 시작"
                sh './gradlew bootJar'

                echo "🐳 Docker 이미지 빌드"
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."

                echo "🔐 Docker Hub 로그인 및 Push"
                sh "docker login -u wkdlrn -p qwer1234qwer"
                sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }

        /*
         * 🚀 [DEPLOY STAGE]
         * - Blue-Green 전략으로 Kubernetes에 배포
         * - 쿠버네티스 마스터(192.168.201.100)에 SSH로 접속하여 배포 실행
         */
        stage('Blue-Green Deploy') {
            agent any
            steps {
                script {
                    def even_or_odd = BUILD_NUMBER.toInteger() % 2
                    def color = even_or_odd == 0 ? 'green' : 'blue'
                    def otherColor = even_or_odd == 0 ? 'blue' : 'green'

                    // 🎯 새로운 버전의 Deployment 생성
                    def deployCommand = """
ssh test@192.168.201.100 "export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl apply -f - --validate=false <<'EOF'
apiVersion: apps/v1
kind: Deployment
metadata:
  namespace: kjg
  name: backend-deployment-${color}
spec:
  selector:
    matchLabels:
      type: backend
      deployment: ${color}
  replicas: 2
  strategy:
    type: RollingUpdate
  minReadySeconds: 10
  template:
    metadata:
      labels:
        type: backend
        deployment: ${color}
    spec:
      containers:
      - name: backend-${color}
        image: ${IMAGE_NAME}:${IMAGE_TAG}
      terminationGracePeriodSeconds: 0
EOF"
""".stripIndent()

                    // 🕐 배포 완료 대기
                    def waitCommand = """
ssh test@192.168.201.100 "export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl rollout status deployment/backend-deployment-${color} -n kjg && kubectl wait --for=condition=available deployment/backend-deployment-${color} --timeout=120s -n kjg"
""".stripIndent()

                    // 📡 서비스 라우팅을 새 버전으로 전환
                    def serviceCommand = """
ssh test@192.168.201.100 "export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl apply -f - --validate=false <<'EOF'
apiVersion: v1
kind: Service
metadata:
  namespace: kjg
  name: backend-svc
spec:
  selector:
    type: backend
    deployment: ${color}
  ports:
    - port: 8080
      targetPort: 8080
  type: LoadBalancer
EOF"
""".stripIndent()

                    // 🧹 이전 버전 scale down
                    def scaleDownCommand = """
ssh test@192.168.201.100 "export KUBECONFIG=/etc/kubernetes/admin.conf && kubectl scale deployment backend-deployment-${otherColor} --replicas=0 -n kjg || true"
""".stripIndent()

                    // 실행 순서대로 배포 실행
                    sh deployCommand
                    sh waitCommand
                    sh serviceCommand
                    sh scaleDownCommand
                }
            }
        }
    }
}
