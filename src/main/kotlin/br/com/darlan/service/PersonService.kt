package br.com.darlan.service

import br.com.darlan.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {
    private val counter: AtomicLong = AtomicLong()
    private val logger = Logger.getLogger(PersonService::class.java.name)


    fun findAll(): List<Person> {
        logger.info("Finding all people!")

        val persons: MutableList<Person> = ArrayList()
        for (i in 0..7) {
            val person = mockPerson()
            persons.add(person)
        }
        return persons
    }

    fun findById(id: Long): Person {
        logger.info("Finding one person!")

        return Person(
            id = counter.incrementAndGet(),
            firstName = "Darlan",
            lastName = "Silva",
            address = "Carapicuiba - SP - Brazil",
            gender = "trans"
        )
    }

    fun create(person: Person) = person
    fun update(person: Person) = person
    fun delete(id: Long) {}


    private fun mockPerson(): Person {
        val id = counter.incrementAndGet()
        return Person(
            id = id,
            firstName = "Darlan $id",
            lastName = "Silva $id",
            address = "Carapicuiba - SP - Brazil $id",
            gender = "trans $id"
        )
    }


}