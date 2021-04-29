pipeline {
	agent none
	options { skipDefaultCheckout(false) }
	stages {
		stage('git pull') {
			agent any
			steps {
				mattermostSend (
                            			color: "#2A42EE", 
                            			message: "Build STARTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                        		)  
				checkout scm
			}
		}
		stage('Docker build') {
			agent any
			steps {
				script {
                    				try {
						sh 'docker build -t frontend:latest /var/jenkins_home/workspace/thxstore-jenkins-cicd/frontend'
						sh 'docker build -t backend:latest /var/jenkins_home/workspace/thxstore-jenkins-cicd/backend'
					}catch(e) {
                        				mattermostSend (
                                					color: "danger", 
                                					message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                            				)
                    				} 
				}
			}
		}
		stage('Docker run') {
			agent any
			steps {
				script {
                    				try {
						sh 'docker ps -f name=frontend -q | xargs --no-run-if-empty docker container stop'
						sh 'docker ps -f name=backend -q | xargs --no-run-if-empty docker container stop'
				
						sh 'docker container ls -a -f name=frontend -q | xargs -r docker container rm'
						sh 'docker container ls -a -f name=backend -q | xargs -r docker container rm'
				
						sh 'docker images -f dangling=true && docker rmi $(docker images -f dangling=true -q)' 

						sh 'docker run -d --name frontend \
						-p 80:80 \
						-p 443:443 \
						-v /home/ubuntu/sslkey/:/var/jenkins_home/workspace/thxstore-jenkins-cicd/sslkey/ \
						-v /etc/localtime:/etc/localtime:ro \
						--network thxstorecicdnetwork \
						frontend:latest'

						sh 'docker run -d --name backend \
						--network thxstorecicdnetwork backend:latest'
					}catch(e) {
						currentBuild.result = "FAILURE"
                    				} finally {
						if(currentBuild.result == "FAILURE"){
							mattermostSend (
                                						color: "danger", 
                                						message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                            					)
						}
						else{
							mattermostSend (
                                						color: "good", 
                                						message: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                            					)
						}
					}
				}
			}
		}
	}
}