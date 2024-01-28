package br.com.darlan.controller

import br.com.darlan.model.Person
import br.com.darlan.service.PersonService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping(value = ["person"])
class PersonController(
    private val service: PersonService,
) {
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findAll(): ResponseEntity<List<Person>> {
        return ResponseEntity.ok().body(service.findAll())
    }

    @GetMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<Person> {
        return ResponseEntity.ok().body(service.findById(id))
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody person: Person, uriBuilder: UriComponentsBuilder): ResponseEntity<Person> {
        val personCreated = service.create(person)
        val uri = uriBuilder.path("person/{id}").buildAndExpand(personCreated.id).toUri()
        return ResponseEntity.created(uri).body(personCreated)

    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(@RequestBody person: Person): ResponseEntity<Person> {
        return ResponseEntity.ok().body(service.update(person))
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}