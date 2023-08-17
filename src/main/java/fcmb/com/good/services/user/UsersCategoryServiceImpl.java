package fcmb.com.good.services.user;

import fcmb.com.good.dto.ApiResponse;
import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.UserCategoryRequest;
import fcmb.com.good.model.dto.response.userResponse.UserCategoryResponse;
import fcmb.com.good.model.entity.user.UserCategory;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.repo.user.UsersCategoryRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersCategoryServiceImpl implements UserCategoryService {

    private  final UsersCategoryRepository usersCategoryRepository;
    private final UsersRepository usersRepository;

    @Override
    /**
     * @Validate and Find the list of all UsersCategory
     * @Validate if the List of UsersCategory is empty otherwise return record not found*
     * @return the list of UsersCategory and a Success Message* *
     * * */
    public ApiResponse<List<UserCategoryResponse>> getListOfUsersCategory(int page, int size) {

        List<UserCategory> userCategoryList = usersCategoryRepository.findAll(PageRequest.of(page,size)).toList();
        if(userCategoryList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label,
                Mapper.convertList(userCategoryList, UserCategoryResponse.class));
    }



    @Override
    /**
     * @Validate that no duplicate UsersCategory is allowed
     * @Validate that Users category exists otherwise return record not found
     * Create the Users definition and save
     * @return success message
     * * */
    public ApiResponse<String> addUsersCategory(UserCategoryRequest request) {

        Optional<UserCategory> usersTypeOptional = validateDuplicateUsersCategory(request.getName());


        if (!usersTypeOptional.isEmpty()) {
            return new ApiResponse("Duplicate Record", AppStatus.FAILED.label,
                    HttpStatus.EXPECTATION_FAILED.value());
        }

        UserCategory userCategory = new UserCategory();

        userCategory.setName(request.getName());
        userCategory.setDescription(request.getDescription());

        usersCategoryRepository.save(userCategory);


        return new ApiResponse("Record Added successfully", AppStatus.SUCCESS.label,
                HttpStatus.OK.value());
    }

    /**
     * @Validating existingUsersCategoryOptional by name
     * @Validate if the List of existingUsersCategoryOptional is empty otherwise return Duplicate Record
     * */
    private Optional<UserCategory> validateDuplicateUsersCategory(String name) {
        Optional<UserCategory> existingUsersCategoryOptional = usersCategoryRepository.findByName(name);
        return existingUsersCategoryOptional;

    }


    /**
     * Set and get the UsersCategory parameters
     */
    private UserCategory getUsersCategoryFromRequest(UserCategoryRequest request) {
        UserCategory userCategory = new UserCategory();

        userCategory.setName(request.getName());
        userCategory.setDescription(request.getDescription());

        return userCategory;
    }

    /**
     * @validating UsersCategoryOptional by uuid
     * @Validate if the List of UsersCategory is empty otherwise return record not found
     * @return UsersCategoryOptional
     * * */
    private UserCategory validateUsersCategory(UUID uuid){
        Optional<UserCategory> usersTypeOptional = usersCategoryRepository.findByUuid(uuid);
        if(usersTypeOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return usersTypeOptional.get();
    }


    @Override
    /**
     * @Validating and Finding the list of productCategoryOptional by uuid
     * @Validate if the List of productCategoryOptional is empty otherwise return record not found
     * Create the productCategory definition and get the productCategoryOptional by uuid
     * @return the list of productCategory and a Success Message
     * * */
    public ApiResponse<UserCategoryResponse> getUsersTypeById(UUID usersCategoryId) {

        Optional<UserCategory> usersTypeOptional = usersCategoryRepository.findByUuid(usersCategoryId);

        if(usersTypeOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        UserCategory userCategory = usersTypeOptional.get();

        return new ApiResponse<UserCategoryResponse>(AppStatus.SUCCESS.label,
                Mapper.convertObject(userCategory,UserCategoryResponse.class));
    }

    @Override
    public ApiResponse<UserCategoryResponse> getUsersTypeByName(String name) {

        Optional<UserCategory> usersTypeOptional = usersCategoryRepository.findByName(name);

        if(usersTypeOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label,
                Mapper.convertObject(usersTypeOptional, UserCategoryResponse.class));

    }


    @Override
    /**
     * @validating UsersCategoryOptional by uuid
     * @Validate if the List of UsersCategoryOptional is empty otherwise return record not found
     * Create the UsersCategory definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateUsersCategory(UUID usersCategoryId, UserCategoryRequest request) {

        UserCategory userCategory = validateUsersCategory(usersCategoryId);

        userCategory.setName(request.getName());
        userCategory.setDescription(request.getDescription());

        usersCategoryRepository.save(userCategory);

        return new ApiResponse<>(AppStatus.SUCCESS.label, "Record Updated Successfully");
    }


    @Override
    /**
     * @validate UsersCategory by uuid
     * @Validate if UsersCategory is empty otherwise return record not found
     * @Delete UsersCategory
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteUsersCategory(UUID usersCategoryId) {

        UserCategory userCategory = validateUsersCategory(usersCategoryId);

        usersCategoryRepository.delete(userCategory);

        return new ApiResponse("Record Deleted successfully", AppStatus.SUCCESS.label,
                HttpStatus.OK.value());
    }



}
