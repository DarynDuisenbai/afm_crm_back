package test.afm_crm.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import test.afm_crm.common.exception.NotFoundException;
import test.afm_crm.contact.dto.ContactRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final MongoTemplate mongoTemplate;

    public List<Contact> getContacts(String search, String status) {
        Query query = new Query();

        if (search != null && !search.isBlank()) {
            Pattern pattern = Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE);
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("name").regex(pattern),
                    Criteria.where("company").regex(pattern),
                    Criteria.where("email").regex(pattern)
            ));
        }

        if (status != null) {
            query.addCriteria(Criteria.where("status").is(ContactStatus.valueOf(status.toUpperCase())));
        }

        return mongoTemplate.find(query, Contact.class);
    }

    public Contact createContact(ContactRequest request) {
        Contact contact = Contact.builder()
                .name(request.getName())
                .company(request.getCompany())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(parseStatus(request.getStatus()))
                .createdAt(LocalDateTime.now())
                .build();

        return contactRepository.save(contact);
    }

    public Contact updateContact(String id, ContactRequest request) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found: " + id));

        contact.setName(request.getName());
        contact.setCompany(request.getCompany());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setStatus(parseStatus(request.getStatus()));

        return contactRepository.save(contact);
    }

    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }

    private ContactStatus parseStatus(String value) {
        if (value == null) return ContactStatus.LEAD;
        return ContactStatus.valueOf(value.toUpperCase());
    }
}
