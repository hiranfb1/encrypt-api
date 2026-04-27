package fernandakipper.com.encrypt_api.services;

import fernandakipper.com.encrypt_api.domain.operation.Operation;
import fernandakipper.com.encrypt_api.domain.operation.exceptions.OperationNotFoundException;
import fernandakipper.com.encrypt_api.dto.OperationDTO;
import fernandakipper.com.encrypt_api.dto.OperationResponseDTO;
import fernandakipper.com.encrypt_api.repositories.OperationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    private final OperationRepository repository;
    private final EncryptService encryptService;

    public OperationService(OperationRepository repository, EncryptService encryptService) {
        this.repository = repository;
        this.encryptService = encryptService;
    }

    public Operation create(OperationDTO operationDTO) {
        Operation operation = new Operation();
        String userDocumentHashed = this.encryptService.encryptData(operationDTO.userDocument());
        String creditCardHashed = this.encryptService.encryptData(operationDTO.creditCardToken());
        operation.setUserDocument(userDocumentHashed);
        operation.setCreditCardToken(creditCardHashed);
        operation.setOperationValue(operationDTO.operationValue());
        this.repository.save(operation);
        return operation;
    }

    public OperationResponseDTO read(Long id) throws OperationNotFoundException {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        String userDocumentHashed = this.encryptService.decryptData(operation.getUserDocument());
        String creditCardHashed = this.encryptService.decryptData(operation.getCreditCardToken());
        OperationResponseDTO dto = new OperationResponseDTO(operation.getId(), userDocumentHashed, creditCardHashed, operation.getOperationValue());
        return dto;
    }

    @Transactional
    public Operation update(OperationDTO data, Long id) throws OperationNotFoundException {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        if (!data.userDocument().isEmpty()) {
            operation.setUserDocument(this.encryptService.encryptData(data.userDocument()));
        }
        if (!data.creditCardToken().isEmpty()) {
            operation.setCreditCardToken(this.encryptService.encryptData(data.creditCardToken()));
        }
        if (data.operationValue() != null) {
            operation.setOperationValue(data.operationValue());
        }
        return operation;
    }

    public void delete(Long id) throws OperationNotFoundException {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        repository.delete(operation);
    }
}