package br.com.vinitefapp.enums;

public enum BitField {

    BIT_CARD_NUMBER(2, MandatoryOrOptional.OPTIONAL),
    BIT_PROCESSING_CODE(3, MandatoryOrOptional.MANDATORY),
    BIT_AMOUNT(4, MandatoryOrOptional.MANDATORY),
    BIT_TRANSACTION_DATETIME(7, MandatoryOrOptional.MANDATORY),
    BIT_TRANSACTION_NSU(11, MandatoryOrOptional.MANDATORY),
    BIT_TRANSACTION_HOUR(12, MandatoryOrOptional.MANDATORY),
    BIT_TRANSACTION_MONTH_DAY(13, MandatoryOrOptional.MANDATORY),
    BIT_CARD_DATE(14, MandatoryOrOptional.OPTIONAL),
    BIT_ENTRY_MODE(22, MandatoryOrOptional.MANDATORY),
    BIT_AUTHORIZATION_CODE(38, MandatoryOrOptional.OPTIONAL),
    BIT_RESPONSE_CODE(39, MandatoryOrOptional.OPTIONAL),
    BIT_TERMINAL_ID(41, MandatoryOrOptional.OPTIONAL),
    BIT_MERCHANT_ID(42, MandatoryOrOptional.MANDATORY),
    BIT_HOLD_NAME(42, MandatoryOrOptional.OPTIONAL),
    BIT_CVV(48, MandatoryOrOptional.OPTIONAL),
    BIT_INSTALLMENT_NUMBER(67, MandatoryOrOptional.MANDATORY),
    BIT_NSU_HOST(127, MandatoryOrOptional.MANDATORY);

    private final int bit;
    private final MandatoryOrOptional mandatoryOrOptional;

    BitField(final int bit, final MandatoryOrOptional mandatoryOrOptional) {
        this.bit = bit;
        this.mandatoryOrOptional = mandatoryOrOptional;
    }

    public int getBit() {
        return bit;
    }

    public MandatoryOrOptional getMandatoryOrOptional() {
        return mandatoryOrOptional;
    }
}

