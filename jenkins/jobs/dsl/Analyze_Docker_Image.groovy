// Folders
def workspaceFolderName = "${WORKSPACE_NAME}"
def projectFolderName = "${PROJECT_NAME}"

// Variables
def projectNameKey = projectFolderName.toLowerCase().replace("/", "-")

// Jobs
def analyzeDocker = freeStyleJob(projectFolderName + "/Analyze_Docker_Image")

analyzeDocker.with{
	label('docker')
    parameters {
        stringParam("DOCKER_IMAGE_ID",,)
    }
	steps {
		shell ('''
		export TMPDIR=/tmp
		analyze-local-images -endpoint "http://adop-clair:6060" \
		-my-address "jenkins-slave" \
		${DOCKER_IMAGE_ID}
		#-my-address "jenkins-slave" \
		''')
	}	
}