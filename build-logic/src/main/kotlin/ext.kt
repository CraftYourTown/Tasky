import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

fun ShadowJar.reloc(vararg clazz: String) {
    clazz.forEach { relocate(it, "uk.mangostudios.tasky.libs.$it") }
}