package system.gc.dtos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Wisley Bruno Marques França
 */
public interface ConvertEntityAndDTO<T, X> {
    default Set<T> convertSetEntityToSetEntityDTO(Set<X> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        Set<T> convertedList = new HashSet<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    default Set<X> convertSetEntityDTOFromSetEntity(Set<T> setEntityDTO) {
        if (setEntityDTO == null || setEntityDTO.isEmpty()) {
            return null;
        }
        Set<X> convertedList = new HashSet<>();
        setEntityDTO.forEach(item -> convertedList.add(toEntity(item)));
        return convertedList;
    }

    default List<T> convertListEntityFromListDTO(List<X> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        List<T> convertedList = new ArrayList<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    default List<X> convertListEntityDTOFromListEntity(List<T> listEntityDTO) {
        if (listEntityDTO == null || listEntityDTO.isEmpty()) {
            return null;
        }
        List<X> convertedList = new ArrayList<>();
        listEntityDTO.forEach(item -> convertedList.add(toEntity(item)));
        return convertedList;
    }

    @Deprecated
    default T convertToDTO(T newDTO, X entity) {
        Method[] entityMethods = entity.getClass().getDeclaredMethods();
        Arrays.stream(entityMethods).forEach(getMethod -> {
            if (getMethod.getName().toUpperCase().contains("GET")
                    && !getMethod.getReturnType().equals(Set.class)
                    && !getMethod.getReturnType().equals(List.class)) {
                try {
                    executeConversion(newDTO, entity, getMethod);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return newDTO;
    }

    @Deprecated
    private void executeConversion(T newDTO, X entity, Method getMethod) throws InvocationTargetException, IllegalAccessException {
        Object returnedValueMethod = getMethod.invoke(entity, null);
        Method[] setMethods = newDTO.getClass().getDeclaredMethods();
        for (Method setMethod : setMethods) {
            if (setMethod.getName().toUpperCase().contains("SET")
                    && getMethod.getName().substring(3).equals(setMethod.getName().substring(3))) {
                setMethod.invoke(newDTO, returnedValueMethod);
                break;
            }
        }
    }

    @Deprecated
    default X convertToEntity(T objectDTO, X newEntity) {
        Method[] entityMethods = objectDTO.getClass().getDeclaredMethods();
        Arrays.stream(entityMethods).forEach(getMethod -> {
            if (getMethod.getName().toUpperCase().contains("GET")
                    && !getMethod.getReturnType().equals(Set.class)
                    && !getMethod.getReturnType().equals(List.class)) {
                try {
                    executeConversionToEntity(objectDTO, newEntity, getMethod);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return newEntity;
    }

    @Deprecated
    private void executeConversionToEntity(T objectDTO, X newEntity, Method getMethod) throws InvocationTargetException, IllegalAccessException {
        Object returnedValueMethod = getMethod.invoke(objectDTO, null);
        Method[] setMethods = newEntity.getClass().getDeclaredMethods();
        for (Method setMethod : setMethods) {
            if (setMethod.getName().toUpperCase().contains("SET")
                    && getMethod.getName().substring(3).equals(setMethod.getName().substring(3))) {
                setMethod.invoke(newEntity, returnedValueMethod);
                break;
            }
        }
    }

    T toDTO(X x);

    X toEntity(T t);
}
