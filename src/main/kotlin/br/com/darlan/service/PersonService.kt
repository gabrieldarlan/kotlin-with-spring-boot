package br.com.darlan.service

import br.com.darlan.controller.PersonController
import br.com.darlan.data.vo.v1.PersonVO
import br.com.darlan.exceptions.ResourceNotFoundException
import br.com.darlan.mapper.DozerMapper
import br.com.darlan.mapper.custom.PersonMapper
import br.com.darlan.model.Person
import br.com.darlan.repository.PersonRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger
import br.com.darlan.data.vo.v2.PersonVO as PersonVOV2

@Service
class PersonService(
    private val repository: PersonRepository,
    private val personMapper: PersonMapper
) {
    private val counter: AtomicLong = AtomicLong()
    private val logger = Logger.getLogger(PersonService::class.java.name)


    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")

        return repository.findAll().let {
            val vos = DozerMapper.parseListObjects(it, PersonVO::class.java)
            vos.forEach { person ->
                val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
                person.add(withSelfRel)
            }
            vos

        }

    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id!")
        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this id") }
        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one person with name ${personVO.firstName}")

        return personVO.let {
            DozerMapper.parseObject(it, Person::class.java)
        }.let { repository.save(it) }
            .let {
                val personVOResponse = DozerMapper.parseObject(it, PersonVO::class.java)
                val withSelfRel = linkTo(PersonController::class.java).slash(personVOResponse.key).withSelfRel()
                personVOResponse.add(withSelfRel)
                personVOResponse
            }
    }

    fun update(personVO: PersonVO): PersonVO {
        logger.info("Updating one person with ID ${personVO.key}")

        return repository
            .findById(personVO.key!!)
            .orElseThrow { ResourceNotFoundException("No records for found this ID") }.copy(
                id = personVO.key,
                firstName = personVO.firstName,
                lastName = personVO.lastName,
                address = personVO.address,
                gender = personVO.gender
            ).let {
                val personVOResponse = DozerMapper.parseObject(it, PersonVO::class.java)
                val withSelfRel = linkTo(PersonController::class.java).slash(personVOResponse.key).withSelfRel()
                personVOResponse.add(withSelfRel)
                personVOResponse
            }

    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id")

        repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }
            .let { repository.delete(it) }
    }

    fun createV2(personVO: PersonVOV2): PersonVOV2 {
        logger.info("Creating one person with name ${personVO.firstName}")

        return personVO.let {
            personMapper.mapVOToEntity(it)
        }.let { repository.save(it) }
            .let { personMapper.mapEntityToVO(it) }
    }

}