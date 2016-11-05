package hello.utils;

import model.fantasy.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {



    public static Pageable generatePageable(Integer size, Integer offset){
        return new PageRequest(offset, size);
    }

    public static Pagination toPagination(Page pageRepsonse, int offset, int size){
        return new Pagination(offset, size, pageRepsonse.getTotalPages(), pageRepsonse.getTotalElements());
    }

}
