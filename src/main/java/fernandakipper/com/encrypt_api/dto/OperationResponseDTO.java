package fernandakipper.com.encrypt_api.dto;

public record OperationResponseDTO(
        Long id,
        String userDocument,
        String creditCardToken,
        Long operationValue) {
}