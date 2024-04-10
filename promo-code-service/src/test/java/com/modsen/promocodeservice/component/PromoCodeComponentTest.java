package com.modsen.promocodeservice.component;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.exceptions.PromoCodeNotFoundException;
import com.modsen.promocodeservice.mapper.PromoCodeMapper;
import com.modsen.promocodeservice.model.PromoCode;
import com.modsen.promocodeservice.repository.PromoCodeRepository;
import com.modsen.promocodeservice.service.impl.PromoCodeServiceImpl;
import com.modsen.promocodeservice.util.ExceptionMessages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.modsen.promocodeservice.util.PromoCodeUtilTest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@CucumberContextConfiguration
public class PromoCodeComponentTest {

    @Mock
    private PromoCodeRepository promoCodeRepository;
    @Mock
    private PromoCodeMapper promoCodeMapper;
    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    private PromoCodeResponse promoCodeResponse;
    private Exception exception;


    @Given("PromoCode with id {string} exist")
    public void PromoCodeWithIdExist(String id) {
        PromoCodeResponse expected = getDefaultPromoCodeResponse();
        PromoCode retrievedPromoCode = getDefaultSavedPromoCode();

        doReturn(Optional.of(retrievedPromoCode))
                .when(promoCodeRepository)
                .findById(id);
        doReturn(expected)
                .when(promoCodeMapper)
                .fromEntityToResponse(retrievedPromoCode);
        doReturn(true)
                .when(promoCodeRepository)
                .existsById(id);

        Optional<PromoCode> promoCode = promoCodeRepository.findById(id);
        assertTrue(promoCode.isPresent());
    }

    @Then("The response should contain promoCode with id {string}")
    public void theResponseShouldContainPromoCodeWithId(String id) {
        PromoCode promoCode = promoCodeRepository.findById(id).get();
        PromoCodeResponse expected = promoCodeMapper.fromEntityToResponse(promoCode);

        assertThat(promoCodeResponse).isEqualTo(expected);
    }

    @When("The id {long} is passed to the findById method")
    public void theIdIsPassedToTheFindByIdMethod(String id) {
        try {
            promoCodeResponse = promoCodeService.getPromoCodeById(id);
        } catch (PromoCodeNotFoundException e) {
            exception = e;
        }

    }

    @Given("PromoCode with id {string} doesn't exist")
    public void promoCodeWithIdExistNotExist(String id) {
        Optional<PromoCode> promoCode = promoCodeRepository.findById(id);
        assertFalse(promoCode.isPresent());
    }

    @When("The id {string} is passed to the delete method")
    public void theIdIsPassedToTheDeleteMethod(String id) {
        try {
            promoCodeService.deletePromoCodeById(id);
        } catch (PromoCodeNotFoundException e) {
            exception = e;
        }
    }

    @Then("The PromoCodeNotFoundException should be thrown with id {string}")
    public void thePromoCodeNotFoundExceptionShouldBeThrown(String id) {
        String expected = String.format(ExceptionMessages.PROMOCODE_NOT_FOUND_BY_ID, id);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Given("A promoCode with value {string} doesn't exist")
    public void aPromoCodeWithValueDoesntExist(String value) {
        PromoCodeResponse expected = getDefaultPromoCodeResponse();
        PromoCode promoCodeToSave = getDefaultNotPromoCode();
        PromoCode savedPassenger = getDefaultSavedPromoCode();
        doReturn(false)
                .when(promoCodeRepository)
                .existsByValue(value);
        doReturn(promoCodeToSave)
                .when(promoCodeMapper)
                .fromRequestToEntity(any(PromoCodeRequest.class));
        doReturn(savedPassenger)
                .when(promoCodeRepository)
                .save(promoCodeToSave);
        doReturn(expected)
                .when(promoCodeMapper)
                .fromEntityToResponse(any(PromoCode.class));

    }

    @When("A create request with value {string} is passed to the add method")
    public void aCreateRequestWithValueIsPassedToTheAddMethod(String value) {
        PromoCodeRequest request = getPromoCodeRequestWithValue(value);
        try {
            promoCodeResponse = promoCodeService.createPromoCode(request);
        } catch (PromoCodeNotFoundException ex) {
            exception = ex;
        }
    }

    @Then("The response should contain created promoCode with value {string}")
    public void theResponseShouldContainCreatedPromoCodeWithValue(String value) {
        var expected = getDefaultPromoCodeResponse();
        assertThat(promoCodeResponse).isEqualTo(expected);
    }

    @When("PromoCode request is passed with id {string} to update method")
    public void promoCodeRequestIsPassedWithIdToUpdateMethod(String id) {
        PromoCodeRequest request = getPromoCodeRequestForUpdate();
        try {
            promoCodeResponse = promoCodeService.updatePromoCode(id, request);
        } catch (PromoCodeNotFoundException ex) {
            exception = ex;
        }
    }

    @Given("PromoCode with id {string} exist to update")
    public void promoCodeWithIdExistToUpdate(String id) {
        PromoCodeRequest updateRequest = getPromoCodeRequestForUpdate();
        PromoCodeResponse expected = getUpdatedPromoCodeResponse();
        PromoCode promoCodeToSave = getUpdatePromoCodeNotSaved();
        PromoCode savedPromoCode = getUpdatedPromoCode();

        doReturn(false)
                .when(promoCodeRepository)
                .existsByValue(updateRequest.getValue());
        doReturn(Optional.of(savedPromoCode))
                .when(promoCodeRepository)
                .findById(id);
        doReturn(promoCodeToSave)
                .when(promoCodeMapper)
                .fromRequestToEntity(any(PromoCodeRequest.class));
        doReturn(savedPromoCode)
                .when(promoCodeRepository)
                .save(any(PromoCode.class));
        doReturn(expected)
                .when(promoCodeMapper)
                .fromEntityToResponse(any(PromoCode.class));

    }

    @Then("The response should contain update promoCode with id {string}")
    public void theResponseShouldContainUpdatePromoCodeWithId(String id) {
        PromoCodeResponse expected = getUpdatedPromoCodeResponse();

        assertThat(promoCodeResponse).isEqualTo(expected);
    }
}
