package br.com.darlan.mapper.custom

import br.com.darlan.data.vo.v2.PersonVO
import br.com.darlan.model.Person
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonMapper {
    fun mapEntityToVO(person: Person) = PersonVO(
        key = person.id!!,
        firstName = person.firstName,
        lastName = person.lastName,
        address = person.address,
        gender = person.gender,
        birthDay = Date()
    )

    fun mapVOToEntity(person: PersonVO) = Person(
        id = person.key,
        firstName = person.firstName,
        lastName = person.lastName,
        address = person.address,
        gender = person.gender,
    )

}