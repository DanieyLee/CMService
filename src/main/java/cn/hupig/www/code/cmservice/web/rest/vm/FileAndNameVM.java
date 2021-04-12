package cn.hupig.www.code.cmservice.web.rest.vm;

import javax.persistence.Lob;

/**
 * View Model object for storing the user's key and password.
 */
public class FileAndNameVM {

	@Lob
    private byte[] file;
	
	private String name;
	
	private boolean fileSwitch;

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fileSwitch
	 */
	public boolean isFileSwitch() {
		return fileSwitch;
	}

	/**
	 * @param fileSwitch the fileSwitch to set
	 */
	public void setFileSwitch(boolean fileSwitch) {
		this.fileSwitch = fileSwitch;
	}

	@Override
	public String toString() {
		return "FileAndNameVM [file=" + file.length + ", name=" + name + ", fileSwitch=" + fileSwitch + "]";
	}
	
}
