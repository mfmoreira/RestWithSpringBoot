package br.com.odeveza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.odeveza.data.vo.v1.UploudFileResponseVO;
import br.com.odeveza.services.FileStorageService;
import io.swagger.annotations.Api;

@Api(tags= "FileEndpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadFile")
	public UploudFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/downloadFile/")
				.path(fileName)
				.toUriString();
		
		return new UploudFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
}
