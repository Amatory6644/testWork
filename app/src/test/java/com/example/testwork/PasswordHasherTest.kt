package com.example.testwork


import com.example.testwork.data.security.PasswordHasher
import org.junit.Assert.*
import org.junit.Test

class PasswordHasherTest {

    @Test
    fun `hash is deterministic and not equal to raw password`() {
        val raw = "MySuperPassword123"
        val hash1 = PasswordHasher.hash(raw)
        val hash2 = PasswordHasher.hash(raw)

        assertEquals(hash1, hash2)
        println("\n$hash1 - hash1\n$hash2 - hash2\n")
        assertNotEquals(raw, hash1)
        println("$raw - raw\n$hash1 - hash1\n")
        assertTrue(hash1.length > 0)
        println("Lenght > 0\n${hash1.length}\n")
    }
}