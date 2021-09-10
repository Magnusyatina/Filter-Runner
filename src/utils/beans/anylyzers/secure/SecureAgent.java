package utils.beans.anylyzers.secure;

/**
 * Secure agent for check access to field
 */
public interface SecureAgent {

    /**
     * Income field check on registry in white list
     */
    boolean check(String field);

    /**
     * Assigning politics to white list
     */
    void addPolicies(Policy[] policies);
}
