package co.com.pruebatecnica.api.mapper;
import co.com.pruebatecnica.api.request.FranchiseJson;
import co.com.pruebatecnica.model.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FranchiseOperationsMapper {
    FranchiseOperationsMapper INSTANCE = Mappers.getMapper( FranchiseOperationsMapper.class );
    Franchise franchiseJsonToModel(FranchiseJson franchiseJson);
}
