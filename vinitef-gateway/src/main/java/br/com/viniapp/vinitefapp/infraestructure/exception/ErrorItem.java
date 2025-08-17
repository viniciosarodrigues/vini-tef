package br.com.viniapp.vinitefapp.infraestructure.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ErrorItem implements Serializable {

    private String fieldName;
    private String message;

    public ErrorItem() {
        super();
    }

    public ErrorItem(String fieldName, String message) {
        super();
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ErrorItem other = (ErrorItem) obj;
        if (fieldName == null) {
            if (other.fieldName != null)
                return false;
        } else if (!fieldName.equals(other.fieldName))
            return false;
        if (message == null) {
            return other.message == null;
        } else return message.equals(other.message);
    }

    @Override
    public String toString() {
        return "ErrorItem [fieldName=" + fieldName + ", message=" + message + "]";
    }

}