package br.com.darlan.controller

import br.com.darlan.data.vo.v1.PersonVO
import br.com.darlan.service.PersonService
import br.com.darlan.util.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import br.com.darlan.data.vo.v2.PersonVO as PersonVOV2

@RestController
@RequestMapping(value = ["/api/person/v1"])
class PersonController(
    private val service: PersonService,
) {
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun findAll(): ResponseEntity<List<PersonVO>> {
        return ResponseEntity.ok().body(service.findAll())
    }

    @GetMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<PersonVO> {
        return ResponseEntity.ok().body(service.findById(id))
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun create(@RequestBody person: PersonVO, uriBuilder: UriComponentsBuilder): ResponseEntity<PersonVO> {
        val personCreated = service.create(person)
        val uri = uriBuilder.path("person/{id}").buildAndExpand(personCreated.id).toUri()
        return ResponseEntity.created(uri).body(personCreated)
    }

    @PostMapping(
        value = ["/v2"],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun createV2(@RequestBody person: PersonVOV2, uriBuilder: UriComponentsBuilder): ResponseEntity<PersonVOV2> {
        val personCreated: PersonVOV2 = service.createV2(person)
        val uri = uriBuilder.path("person/{id}").buildAndExpand(personCreated.id).toUri()
        return ResponseEntity.created(uri).body(personCreated)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun update(@RequestBody person: PersonVO): ResponseEntity<PersonVO> {
        return ResponseEntity.ok().body(service.update(person))
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML]
    )
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}