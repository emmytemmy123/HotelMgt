package fcmb.com.good.services.user;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.UserTypeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.UserTypeResponse;
import fcmb.com.good.model.entity.user.UserType;
import fcmb.com.good.repo.user.UserTypeRepository;
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
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    private final JwtFilter jwtFilter;


    @Override
    /**
     * @Finding the list of UserType
     * @Validate if the List of UserType is empty otherwise return record not found
     * @return the list of UserType and a Success Message*
     * * */
    public ApiResponse<List<UserTypeResponse>> getListOfUserType(int page, int size) {
        List<UserType> typeList = userTypeRepository.findAll(PageRequest.of(page,size)).toList();
        if(typeList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(typeList, UserTypeResponse.class));

    }

    @Override
    /**
     * @Validate that no duplicate UserType is allowed*
     * @Validate that UserType exists otherwise return record not found*
     * Create UserType definition and save
     * @return success message
     * * */
    public ApiResponse<String> addUserType(UserTypeRequest request) {
            UserType type = Mapper.convertObject(request,UserType.class);
            userTypeRepository.save(type);

            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    "Record Added Successfully");
        }



    @Override
    /**
     * @Finding the list of UserTypeOptional by uuid*
     * @Validate if the List of UserTypeOptional is empty otherwise return record not found
     * Create the UserType definition and get the customer
     * @return the list of UserType and a Success Message
     * * */
    public ApiResponse<UserTypeResponse> getUserTypeById(UUID userTypeId) {
        Optional<UserType> type = userTypeRepository.findByUuid(userTypeId);
        if(type.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        UserType cm = type.get();
        return new ApiResponse<UserTypeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,UserTypeResponse.class));
    }


    /**
     * @validating UserTypeOptional by uuid
     * @Validate if the List of UserType is empty otherwise return record not found
     * @return UserTypeOptional
     * * */
    private UserType validateUserType(UUID uuid){
        Optional<UserType> userType = userTypeRepository.findByUuid(uuid);
        if(userType.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return userType.get();
    }

    @Override
    /**
     * @validating UserTypeOptional by uuid
     * @Validate if the List of UserType is empty otherwise return record not found
     * Create the UserType definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateUserType(UUID userTypeId, UserTypeRequest request) {
        UserType userType = validateUserType(userTypeId);
        userType.setType(request.getType());

        userTypeRepository.save(userType);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    /**
     * @validating UserType by uuid
     * @Validate if UserType is empty otherwise return record not found
     * @Delete UserType
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteUserType(UUID usertypeId) {
        UserType type = validateUserType(usertypeId);
        userTypeRepository.delete(type);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");    }
}
