package br.com.viniapp.vinitefapp.infraestructure.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StandardErrorSpring implements Serializable {

    private Long timestamp;
    private Integer status;
    private String error;
    private List<ErrorItem> validations = new ArrayList<>();
    private String message;
    private String path;

    public StandardErrorSpring(Long timestamp, Integer status, String error, List<ErrorItem> validations, String message, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.validations = validations;
        this.message = message;
        this.path = path;
    }

    public StandardErrorSpring() {
        super();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((validations == null) ? 0 : validations.hashCode());
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
        StandardErrorSpring other = (StandardErrorSpring) obj;
        if (error == null) {
            if (other.error != null)
                return false;
        } else if (!error.equals(other.error))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (validations == null) {
            return other.validations == null;
        } else return validations.equals(other.validations);
    }

    @Override
    public String toString() {
        return "StandardErrorSpring [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", validations=" + validations
                + ", message=" + message + ", path=" + path + "]";
    }

}
