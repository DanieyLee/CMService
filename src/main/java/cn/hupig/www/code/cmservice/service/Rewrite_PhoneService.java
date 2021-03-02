package cn.hupig.www.code.cmservice.service;

/**
 * Service Interface for managing {@link cn.hupig.www.code.cmservice.domain.Phone}.
 */
public interface Rewrite_PhoneService {

    /**
     * Send code information to phone.
     *
     * @param phoneDTO the entity to save.
     * @return the persisted entity.
     */
	void sendCode(String phoneNumber);
   
}
