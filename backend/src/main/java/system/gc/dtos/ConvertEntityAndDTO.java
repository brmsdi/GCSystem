package system.gc.dtos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Wisley Bruno Marques França
 */
public interface ConvertEntityAndDTO<DTO, E> {
    default Set<DTO> convertSetEntityToSetEntityDTO(Set<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        Set<DTO> convertedList = new HashSet<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    default Set<E> convertSetEntityDTOFromSetEntity(Set<DTO> setEntityDTO) {
        if (setEntityDTO == null || setEntityDTO.isEmpty()) {
            return null;
        }
        Set<E> convertedList = new HashSet<>();
        setEntityDTO.forEach(item -> convertedList.add(toEntity(item)));
        return convertedList;
    }

    default List<DTO> convertListEntityFromListDTO(List<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        List<DTO> convertedList = new ArrayList<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    default List<E> convertListEntityDTOFromListEntity(List<DTO> listEntityDTO) {
        if (listEntityDTO == null || listEntityDTO.isEmpty()) {
            return null;
        }
        List<E> convertedList = new ArrayList<>();
        listEntityDTO.forEach(item -> convertedList.add(toEntity(item)));
        return convertedList;
    }

    @Deprecated
    default DTO convertToDTO(DTO newDTO, E entity) {
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
    private void executeConversion(DTO newDTO, E entity, Method getMethod) throws InvocationTargetException, IllegalAccessException {
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
    default E convertToEntity(DTO objectDTO, E newEntity) {
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
    private void executeConversionToEntity(DTO objectDTO, E newEntity, Method getMethod) throws InvocationTargetException, IllegalAccessException {
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

    DTO toDTO(E e);

    E toEntity(DTO DTO);
}
