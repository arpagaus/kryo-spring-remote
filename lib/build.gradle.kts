tasks.jar {
    enabled = true
}

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-web")
    compileOnly("javax.servlet:javax.servlet-api")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("com.esotericsoftware:kryo:5.0.0-RC2")
}
