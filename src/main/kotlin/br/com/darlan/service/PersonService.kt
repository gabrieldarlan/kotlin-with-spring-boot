package br.com.darlan.service

import br.com.darlan.exceptions.ResourceNotFoundException
import br.com.darlan.model.Person
import br.com.darlan.repository.PersonRepository
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService(
    private val repository: PersonRepository,
) {
    private val counter: AtomicLong = AtomicLong()
    private val logger = Logger.getLogger(PersonService::class.java.name)


    fun findAll(): List<Person> {
        logger.info("Finding all people!")

        return repository.findAll()

    }

    fun findById(id: Long): Person {
        logger.info("Finding one person!")

        return repository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("No records found this ID") }

    }

    fun create(person: Person): Person {
        logger.info("Creating one person with name ${person.firstName}")
        return repository.save(person)
    }

    fun update(person: Person): Person {
        logger.info("Updating one person with ID ${person.id}")
//        val entity = repository
//            .findById(person.id!!)
//            .orElseThrow { ResourceNotFoundException("No records for found this ID") }
//            .copy(
//                id = null,
//                firstName = "",
//                lastName = "",
//                address = "",
//                gender = ""
//            )
//            .let { repository.save(it) }

        return repository
            .findById(person.id!!)
            .orElseThrow { ResourceNotFoundException("No records for found this ID") }
            .copy(
                id = person.id,
                firstName = person.firstName,
                lastName = person.lastName,
                address = person.address,
                gender = person.gender
            )
            .let { repository.save(it) }


//        entity.firstName = person.firstName
//        entity.lastName = person.lastName
//        entity.address = person.address
//        entity.gender = person.gender
//
//        return repository.save(entity)

    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id")

        repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }
            .let { repository.delete(it) }


    }

}