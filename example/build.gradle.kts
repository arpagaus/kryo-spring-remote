dependencies {
    compile(project(":lib"))
    implementation("com.esotericsoftware:kryo:5.0.0-RC2")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
