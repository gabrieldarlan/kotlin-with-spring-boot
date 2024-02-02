package br.com.darlan.service

import br.com.darlan.data.vo.v1.PersonVO
import br.com.darlan.exceptions.ResourceNotFoundException
import br.com.darlan.mapper.DozerMapper
import br.com.darlan.mapper.custom.PersonMapper
import br.com.darlan.model.Person
import br.com.darlan.repository.PersonRepository
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
            DozerMapper.parseListObjects(it, PersonVO::class.java)
        }

    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person!")

        return repository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("No records found this ID") }
            .let {
                DozerMapper.parseObject(it, PersonVO::class.java)
            }

    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one person with name ${personVO.firstName}")

        return personVO.let {
            DozerMapper.parseObject(it, Person::class.java)
        }.let { repository.save(it) }
            .let { DozerMapper.parseObject(it, PersonVO::class.java) }
    }

    fun update(personVO: PersonVO): PersonVO {
        logger.info("Updating one person with ID ${personVO.id}")

        return repository
            .findById(personVO.id!!)
            .orElseThrow { ResourceNotFoundException("No records for found this ID") }.copy(
                id = personVO.id,
                firstName = personVO.firstName,
                lastName = personVO.lastName,
                address = personVO.address,
                gender = personVO.gender
            ).let {
                DozerMapper.parseObject(it, PersonVO::class.java)
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