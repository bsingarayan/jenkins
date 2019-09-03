#!/usr/bin/groovy
@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def workDir = SEED_JOB.getWorkspace()
print("Loading config from ${workDir}/config.yml")
def config = new Yaml().load(("${workDir}/config.yml" as File).text)

for (service in config.services) {
    for (stage in config.target_envs) {
        folder("${config.folder_path}/to-${stage}") {
            displayName("Deploy to ${stage} jobs")
            description("Deploy ECS services to ${stage}")
        }

        if (stage == "stage") {
            stage_trigger = """
            pipelineTriggers([cron["1 1 * * 1"])
"""
        } else {
            stage_trigger = ""
        }

        pipelineJob("${config.folder_path}/to-${stage}/${service}") {
            definition {
                cps {
                    sandbox()
                    script("""
    node {
        properties([
            ${stage_trigger}
            parameters([
                choice(
                    choices: ['dev,stage'],
                    description: 'The source environment to promote',
                    name: 'sourceEnv'
                ),
                string(
                    defaultValue: '',
                    description: 'Specify a specific Docker image tag to deploy. This will override sourceEnv and should be left blank',
                    name: 'sourceTag',
                    trim: true
                )
            ])
        ])

        properties([
            disableConcurrentBuilds(),
        ])

        stage('init') {
            dockerPromote(
                app="${service}",
                destinationEnv="${stage}"
            )
        }
    }
                    """)
                }
            }
        }
    }
}
