package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;

public record MedicalResponseData(Long id, String email, String specialization, String DNI, String phone, String name, AddressData address) {}