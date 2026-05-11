package test.afm_crm.contact;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {

    long countByStatus(ContactStatus status);
}
