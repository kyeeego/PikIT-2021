package ru.kyeeego.pikit.modules.requisition.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Set;

@Data
public class RequisitionCreateDto {

    @NotNull
    private String title;

    @NotNull
    private String body;

    @NotNull
    private String cost;

    private String adress;

    // Если клиент хочет загрузить файл, он обращается по маршруту загрузки файлов, сервер
    // сохраняет файл и отправляет в качестве ответа ссылку на файл, которую клиентское приложение
    // добавляет в CreateRequisitionDto на своей стороне.
    //
    // При создании будет проверяться наличие
    // всех файлов в файловой системе, и при отстутствии какого-либо из них сервер
    // будет удалять эту ссылку из списка.
    //
    // В БД всегда будет храниться этот список, даже если пустой

}
