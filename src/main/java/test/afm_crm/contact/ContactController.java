package test.afm_crm.contact;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.afm_crm.contact.dto.ContactRequest;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    @Operation(summary = "Get contacts with optional search and status filter")
    public ResponseEntity<List<Contact>> getContacts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(contactService.getContacts(search, status));
    }

    @PostMapping
    @Operation(summary = "Create a contact")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(contactService.createContact(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contact")
    public ResponseEntity<Contact> updateContact(
            @PathVariable String id,
            @Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(contactService.updateContact(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contact")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
