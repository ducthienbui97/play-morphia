import xerial.sbt.Sonatype.GitHubHosting

sonatypeProfileName := "io.github.ducthienbui97"

publishMavenStyle := true

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting := Some(GitHubHosting("ducthienbui97", "play-morphia", "thienbui797@gmail.com"))