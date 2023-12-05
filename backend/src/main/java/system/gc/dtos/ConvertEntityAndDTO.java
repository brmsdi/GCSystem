package system.gc.dtos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface ConvertEntityAndDTO<DTO, E> {
    /**
     * Este método converte as entidades em DTOS para serem enviados nas respostas das requisições
     * @param entityList - Coleção de entidades
     * @return Coleção de entidades convertidades para DTO
     */
    default Set<DTO> convertSetEntityToSetEntityDTO(Set<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        Set<DTO> convertedList = new HashSet<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    /**
     * Este método converte os DTOS em entidades
     * @param setEntityDTO - Coleção de DTOS
     * @return Coleção de entidades
     */
    default Set<E> convertSetEntityDTOToSetEntity(Set<DTO> setEntityDTO) {
        if (setEntityDTO == null || setEntityDTO.isEmpty()) {
            return null;
        }
        Set<E> convertedList = new HashSet<>();
        setEntityDTO.forEach(item -> convertedList.add(toEntity(item)));
        return convertedList;
    }

    /**
     * Este método converte as entidades em DTOS para serem enviados nas respostas das requisições
     * @param entityList - Coleção de entidades
     * @return Coleção de entidades convertidades para DTO
     */
    default List<DTO> convertListEntityToListDTO(List<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        List<DTO> convertedList = new ArrayList<>();
        entityList.forEach(item -> convertedList.add(toDTO(item)));
        return convertedList;
    }

    /**
     * Este método converte os DTOS em entidades
     * @param listEntityDTO - Coleção de DTOS
     * @return Coleção de entidades
     */
    default List<E> convertListEntityDTOToListEntity(List<DTO> listEntityDTO) {
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
        Object returnedValueMethod = getMethod.invoke(entity, new Object() {});
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
        Object returnedValueMethod = getMethod.invoke(objectDTO, new Object() {});
        Method[] setMethods = newEntity.getClass().getDeclaredMethods();
        for (Method setMethod : setMethods) {
            if (setMethod.getName().toUpperCase().contains("SET")
                    && getMethod.getName().substring(3).equals(setMethod.getName().substring(3))) {
                setMethod.invoke(newEntity, returnedValueMethod);
                break;
            }
        }
    }

    /**
     * @param e - Entidade que será convertida para DTO
     * @return DTO - Entidade convertida para DTO
     */
    DTO toDTO(E e);

    /**
     * @param DTO - DTO que será convertido para entidade
     * @return E - DTO convertido para entidade
     */
    E toEntity(DTO DTO);
}
