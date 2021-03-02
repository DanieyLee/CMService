package cn.hupig.www.code.cmservice.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.hupig.www.code.cmservice.domain.Phone;

/**
 * Spring Data  repository for the Phone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
	
	Phone findOneByPhoneAndCode(String phone, Integer code);
	
	Phone findFirstByPhoneAndEffectiveTimeAfter(String phone, Instant effectiveTime);
	
}
