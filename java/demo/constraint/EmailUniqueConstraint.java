package demo.constraint;


import demo.entity.Poslodavac;
import demo.entity.Radnik;
import demo.repository.PoslodavacRepository;
import demo.repository.RadnikRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailUniqueConstraint.EmailUniqueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUniqueConstraint {
    String message() default " Email adresa vec postoji";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class EmailUniqueValidator implements
            ConstraintValidator<EmailUniqueConstraint, String> {

        @Autowired
        private PoslodavacRepository poslodavacRepository;
        @Autowired
        private RadnikRepository radnikRepository;

        @Override
        public void initialize(EmailUniqueConstraint emailUniqueConstraint) {
        }

        @Override
        public boolean isValid(String email,
                               ConstraintValidatorContext cxt) {

            if (poslodavacRepository == null || radnikRepository == null)
                return true;

            for (Poslodavac poslodavac : poslodavacRepository.findAll()) {
                if (poslodavac.getEmail().equals(email))
                    return false;
            }

            for (Radnik radnik : radnikRepository.findAll()) {
                if (radnik.getEmail().equals(email))
                    return false;
            }

            return true;
        }

    }
}
