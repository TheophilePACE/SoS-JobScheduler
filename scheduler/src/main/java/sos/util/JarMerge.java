/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/

package sos.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

public class JarMerge {
	
// Eigenschaften der Kommandozeile
	
	// Kommandos
	private boolean check = false;
	private boolean unzip = false;
	private boolean zip = false;
	private boolean add = false;
	
	// Schalter
	private boolean silent = false; // unterdr�ckt bei check die Ausgabe, falls keine Duplikate gefunden werden
	private boolean oldOverwrites = false; // bei unzip: wenn true, alte Dateien �berschreiben neue
	private boolean standardManifestToCreate = false;
	private Pattern fileFilterPattern = Pattern.compile(".*");
	private int compression = -1;
	// l�st die Dateien des jars von der Paketstruktur => Dateien werden direkt ins Zielverzeichnis entpackt
	private boolean detach = false;
	
	// Dateiparameter
	private String source = null;
	private String targetPath = null;
	private String manifestFile = null;
	
	// Der Name dieser Klasse
	private String identity = null;
	
	private JarMerge(String[] args) {
		
		this.identity = this.getClass().getName();
		
		try {
			
			this.parseArguments(args);
			
		} catch (Exception e) {
			this.loglnError("\n abort: "+e.getMessage());
			this.printUsage();
			System.exit(-1);
		}
	}
	
	private void log(String msg) {
		System.out.print(msg);
	}
	
	private void logln(String msg) {
		System.out.println(msg);
	}
	
	private void loglnError(String msg) {
		System.err.println(msg);
	}
	
	private void printUsage() {
		
		String usage = "\n usage:";
		usage += " java "+this.identity+" [-s] check PATH";
		usage += "\n";
		usage += "\n        -all jar/zip archives in directory PATH are tested";
		usage += "\n         for duplicate classnames respecting the package structure";
		usage += "\n         -s: silent mode, if no duplicates are found nothing will be written to console";
		usage += "\n";
		usage += "\n        java "+this.identity+" [-o] [-f=REGEXP] [-d] unzip SOURCE [TARGETPATH]";
		usage += "\n";
		usage += "\n        -extract all files contained in jar/zip archive(s) to subdirectories";
		usage += "\n         relative to package structure.";
		usage += "\n         SOURCE can be a directory or a single archive.";
		usage += "\n         If the directory TARGETPATH is set the structure of subdirectories is created there.";
		usage += "\n         By default the structure of subdirectories is created in current working directory.";
		usage += "\n         If the flag -o is set older files overwrite newer ones.";
		usage += "\n         By default newer files overwrite older ones.";
		usage += "\n         -f: file filter for extracting, REGEXP is a regular expression";
		usage += "\n         -d: (detach) all files are extracted directly to target directory";
		usage += "\n             without attending to package structure";
		usage += "\n";
		usage += "\n        java "+this.identity+" [-m] [-f=REGEXP] [-c=LEVEL] zip SOURCEPATH JARFILE [MANIFESTFILE]";
		usage += "\n";
		usage += "\n        -all files of directory SOURCEPATH and subdirectories are compressed";
		usage += "\n         to jar archive file JARFILE.";
		usage += "\n         If a manifest file is set for MANIFESTFILE this file will be added to jar archive.";
		usage += "\n         If the flag -m is set a standard manifest will be created.";
		usage += "\n         This flag will be ignored if MANIFESTFILE is set.";
		usage += "\n         By default it will be searched in SOURCEPATH for '/META-INF/MANIFEST.MF'.";
		usage += "\n         If this manifest file is found it will be added to jar archive.";
		usage += "\n         If this manifest file is not found a standard manifest will be created.";
		usage += "\n         -f: file filter for compression, REGEXP is a regular expression";
		usage += "\n         -c: compression level, LEVEL can be 0 to 9";
		usage += "\n";
		usage += "\n        java "+this.identity+" [-m] [-f=REGEXP] [-c=LEVEL] add SOURCEPATH JARFILE [MANIFESTFILE]";
		usage += "\n";
		usage += "\n        -all files of directory SOURCEPATH and subdirectories are added";
		usage += "\n         to existing jar archive file JARFILE.";
		usage += "\n         If JARFILE does not yet exist a new file will be created.";
		usage += "\n         If a manifest file is set for MANIFESTFILE this file will be added to jar archive.";
		usage += "\n         If the flag -m is set a standard manifest will be created.";
		usage += "\n         This flag will be ignored if MANIFESTFILE is set.";
		usage += "\n         By default it will be searched in SOURCEPATH for '/META-INF/MANIFEST.MF'.";
		usage += "\n         If this manifest file is found it will be added to jar archive.";
		usage += "\n         If this manifest file is not found a standard manifest will be created.";
		usage += "\n         -f: file filter for compression, REGEXP is a regular expression";
		usage += "\n         -c: compression level, LEVEL can be 0 to 9. It affects all files in the archive.";
		usage += "\n";
		usage += "\n        java "+this.identity+" -h";
		usage += "\n";
		usage += "\n        -show this help";
		
		this.logln(usage);
	}
	
	private void parseArguments(String[] args) throws Exception {
		
		if ( args==null ) throw new Exception("args is null");
		if ( args.length==0 ) throw new Exception("arguments missing");
		
		// Hilfe
		if ( args[0].toLowerCase().equals("-h") ) {
			
			if ( args.length>1 ) {
				String arg = args[1];
				if ( arg.equals("check") || arg.equals("unzip") || arg.equals("zip") || arg.equals("add") )
					throw new Exception("unexpected command '"+arg+"'");
				
				if ( args[1].length()<2 )
					throw new Exception("unknown parameter '"+args[1]+"'");
				
				arg = args[1].substring(0,2).toLowerCase();
				if ( arg.equals("-s") || arg.equals("-o") || arg.equals("-f") || arg.equals("-m") || arg.equals("-c") || arg.equals("-d") || arg.equals("-h") )
					throw new Exception("unexpected parameter '"+args[1]+"'");
				else
					throw new Exception("unknown parameter '"+args[1]+"'");
			}
			
			this.printUsage();
			System.exit(0);
		}
		
		ArrayList arguments = new ArrayList();
		ArrayList switches = new ArrayList();
		
		for (int i=0; i<args.length; i++) {
			
			if ( args[i].charAt(0)=='-' ) {
				if ( args[i].length()==1 ) {
					if ( args.length==1 )
						throw new Exception("unknown command '-'");
					else
						throw new Exception("unknown parameter '-'");
				} else {
					switches.add(args[i]);
				}
			} else
				arguments.add(args[i]);
		}
		
		if ( arguments.size()==0 ) throw new Exception("unknown command '"+args[0]+"'");
		String command = (String)arguments.get(0);
		int minNrOfArgs = 0;
		int maxNrOfArgs = 0;
		
		if ( command.equals("check") ) {
			this.check = true;
			minNrOfArgs = 1;
			maxNrOfArgs = 1;
		}
		else
		if ( command.equals("unzip") ) {
			this.unzip = true;
			minNrOfArgs = 1;
			maxNrOfArgs = 2;
		}
		else
		if ( command.equals("zip") ) {
			this.zip = true;
			minNrOfArgs = 2;
			maxNrOfArgs = 3;
		}
		else
		if ( command.equals("add") ) {
			this.add = true;
			minNrOfArgs = 2;
			maxNrOfArgs = 3;
		}
		else
			throw new Exception("unknown command '"+command+"'");
		
		// das Kommando aus der Argumentliste enfernen
		arguments.remove(0);
		
		// Anzahl der restlichen Argumente �berpr�fen
		if ( arguments.size()<minNrOfArgs ) throw new Exception("arguments missing");
		if ( arguments.size()>maxNrOfArgs ) throw new Exception("too many arguments");
		
		String sw = null;
		
	// check
		if ( this.check ) {
			this.source = (String)arguments.get(0);
			// switches
			for (int i=0; i<switches.size(); i++) {
				sw = ((String)switches.get(i)).toLowerCase();
				
				if ( sw.equals("-s") )
					this.silent = true;
				else if ( sw.equals("-o") || sw.equals("-m") || sw.equals("-h") || sw.equals("-d") || sw.matches("-f=.+") || sw.matches("-c=.+") )
					throw new Exception("unexpected parameter '"+(String)switches.get(i)+"'");
				else
					throw new Exception("unknown parameter '"+(String)switches.get(i)+"'");
			}
		}
		else
		
	// unzip
		if ( this.unzip ) {
			this.source = (String)arguments.get(0);
			if ( arguments.size()==2 ) this.targetPath = (String)arguments.get(1);
			// switches
			for (int i=0; i<switches.size(); i++) {
				sw = ((String)switches.get(i)).toLowerCase();
				
				if ( sw.equals("-d") )
					this.detach = true;
				else if ( sw.equals("-o") )
					this.oldOverwrites = true;
				else if ( sw.matches("-f=.+") )
					try {
						this.fileFilterPattern = Pattern.compile( ((String)switches.get(i)).substring(3) );
					} catch (Exception e) {
						throw new Exception("cannot compile pattern '"+((String)switches.get(i)).substring(3)+"'");
					}
				else if ( sw.equals("-s") || sw.equals("-m") || sw.equals("-h") || sw.matches("-c=.+"))
					throw new Exception("unexpected parameter '"+(String)switches.get(i)+"'");
				else
					throw new Exception("unknown parameter '"+(String)switches.get(i)+"'");
			}
		}
		else
		
	// zip & add
		if ( this.zip || this.add ) {
			this.source = (String)arguments.get(0);
			this.targetPath = (String)arguments.get(1);
			if ( arguments.size()==3 ) this.manifestFile = (String)arguments.get(2);
			// switches
			for (int i=0; i<switches.size(); i++) {
				sw = ((String)switches.get(i)).toLowerCase();
				
				if ( sw.equals("-m") )
					this.standardManifestToCreate = true;
				else if ( sw.matches("-f=.+") )
					try {
						this.fileFilterPattern = Pattern.compile( ((String)switches.get(i)).substring(3) );
					} catch (Exception e) {
						throw new Exception("cannot compile pattern '"+((String)switches.get(i)).substring(3)+"'");
					}
				else if ( sw.matches("-c=.+") ) {
					try {
						this.compression = Integer.parseInt( ((String)switches.get(i)).substring(3) );
					} catch (Exception e) {
						throw new Exception("cannot convert '"+((String)switches.get(i)).substring(3)+"' to int");
					}
					if ( this.compression<0 || this.compression>9 ) throw new Exception("level of compression must be between 0 and 9");
				} else if ( sw.equals("-s") || sw.equals("-o") || sw.equals("-d") || sw.equals("-h") )
					throw new Exception("unexpected parameter '"+(String)switches.get(i)+"'");
				else
					throw new Exception("unknown parameter '"+(String)switches.get(i)+"'");
			}
		}
		
	}
	
	/**
	 * Filtert aus einer Liste von Dateinamen alle Dateinamen mit der Endung jar oder zip heraus. 
	 * @return gefilterte Liste
	 */
	private String[] archiveFilter(String[] list) {
		
		String[] fList;
		String suffix;
		int deleted = 0;
		
		if ( list==null ) return null;
		
		for (int i=0; i<list.length; i++) {
			
			if ( list[i].length()<4 || list[i]==null ) {
				list[i] = null;
				deleted++;
			} else {
				suffix = list[i].substring(list[i].length()-4);
				if ( !(suffix.equals(".jar") || suffix.equals(".zip")) ) {
					list[i] = null;
					deleted++;
				}
			}
		}
		
		fList = new String[list.length-deleted];
		
		for (int n=0, i=0; i<list.length; i++) {
			
			if ( list[i]!=null ) fList[n++] = list[i];
		}
		
		return fList;
	}
	
	/**
	 * Filtert aus einer Liste von Dateien alle Dateien, die dem regul�ren Ausdruck pattern gen�gen heraus.
	 * Unabh�ngig davon werden das zu erstellende jar und META-INF/MANIFEST.MF entfernt.
	 * @return gefilterte Liste von Dateien
	 */
	private File[] fileFilter(File[] list, Pattern pattern) throws Exception {
		
		String newjarPath = (new File(this.targetPath)).getCanonicalPath();
		
		File[] fList;
		int deleted = 0;
		
		if ( list==null ) return null;
		
		for (int i=0; i<list.length; i++) {
			// die zu erstellende jardatei soll auch weggefiltert werden
			// das Manifest wird extra hinzugef�gt => hier wegfiltern
			if ( list[i]==null
					|| list[i].getCanonicalPath().equals(newjarPath)
					|| Pattern.matches(".*META-INF[/\\\\]MANIFEST[.]MF", list[i].getPath())
					|| !(pattern.matcher(list[i].getName()).matches())
					) {
				
				list[i] = null;
				deleted++;
			}
		}
		
		fList = new File[list.length-deleted];
		
		for (int n=0, i=0; i<list.length; i++)
			if ( list[i]!=null ) fList[n++] = list[i];
		
		return fList;
	}
	
	private void executeCheck() throws Exception {
		
		JarFile jar;
		Enumeration content;
		JarEntry entry;
		String classFile;
		long lDate;
		long size;
		
		StringTokenizer st;
		SimpleDateFormat sdf;
		String firstFile;
		String firstDate;
		String firstSize;
		int gap;
		int duplicates = 0;
		
		File file = new File(this.source);
		
		// alle zu durchsuchenden jar- und zip-Archive ermitteln
		String[] list = this.archiveFilter(file.list());
		
		if ( list.length == 0 ) {
			this.logln("\n no jar/zip archives found");
			return;
		}
		
		Hashtable inventory = new Hashtable();
		
		try {
			
			int i = 0;
			// alle Archive durchgehen
			for (i=0; i<list.length; i++) {
				
				jar = new JarFile(this.source + list[i]);
				
				content = jar.entries();
				
				// alle Klassen eines Archivs durchgehen
				while ( content.hasMoreElements() ) {
					
					entry = (JarEntry)content.nextElement();
					classFile = entry.getName();
					lDate = entry.getTime();
					size = entry.getSize();
					
					if ( classFile.length()>6 )
						if ( classFile.substring(classFile.length()-6).equals(".class") )
							if ( !inventory.containsKey(classFile) ) {
								
								// 								classFile => "x.jar|date|size"
								inventory.put(classFile, list[i]+"|"+String.valueOf(lDate)+"|"+String.valueOf(size));
								
							} else {
								
								st = new StringTokenizer( (String)inventory.get(classFile), "|" );
								sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								
								firstFile = st.nextToken();
								firstDate = st.nextToken();
								firstSize = st.nextToken();
								
								if ( Long.parseLong(firstDate) < 0 )
									firstDate = "unknown";
								else
									firstDate = sdf.format(new Date(Long.parseLong(firstDate)));
								
								if ( Long.parseLong(firstSize) < 0 )
									firstSize = "unknown";
								
								// die gr��te Stringl�nge ermitteln
								gap = Math.max(firstFile.length(), firstDate.length());
								gap = Math.max(gap, firstSize.length());
								
								// und Stringl�nge normalisieren
								while ( firstFile.length()<gap ) firstFile += " ";
								while ( firstDate.length()<gap ) firstDate += " ";
								while ( firstSize.length()<gap ) firstSize += " ";
								
								this.logln("\n duplicate class found: "+classFile);
								this.logln(" "+ firstFile +"   "+ list[i] );	// Dateiname
								this.logln(" "+ firstDate +"   "+ ( lDate < 0 ? "unknown" : sdf.format(new Date(lDate)) ) );	// Datum								
								this.logln(" "+ firstSize +"   "+ ( size < 0 ? "unknown" : String.valueOf(size) ) );	// Dateigr��e
								
								duplicates++;
							}
				}
			}
			
			if (duplicates==0) {
				if ( !this.silent ) {
					this.logln("\n no duplicates found");
					this.logln(" "+i+" archive"+ ((i!=1) ? "s" : "") +" passed");
				}
			} else {
				this.logln("\n "+duplicates+" duplicate"+ ((duplicates!=1) ? "s" : "") +" found");
				this.logln(" "+i+" archive"+ ((i!=1) ? "s" : "") +" passed");
			}
			
		} catch (Exception e) {
			this.loglnError("check error: "+e.getMessage());
		}
	}
	
	/**
	 * @return Absoluter Pfad der erstellten Datei
	 */
	private String unpackJarEntry( JarFile jarFile, JarEntry jarEntry, String targetPath) throws JarException, IOException, Exception {
		
		try {
			
			if ( jarFile==null ) throw new Exception("jarFile is null");
			if ( jarEntry==null ) throw new Exception("jarEntry is null");
			if ( targetPath==null ) targetPath = "";
			
			String filename = jarEntry.getName();
			
			// Paketstruktur aufl�sen und Datei ohne Unterverzeichnisse direkt nach targetPath entpacken
			if ( this.detach ) {
				int index = filename.lastIndexOf('/') + 1; // behandelt R�ckgabewert -1 implizit mit
				filename = filename.substring(index);
			}
			
			File file = new File( targetPath + filename );
			
			BufferedInputStream bis = new BufferedInputStream( jarFile.getInputStream( jarEntry ) );
			File dir = new File( file.getParent() );
			dir.mkdirs();
			
			BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( file ) );
			
			for ( int c; ( c = bis.read() ) != -1; )
				bos.write( (byte)c );
			
			bos.close();
			bis.close();
			
			file = new File( targetPath + filename );
			file.setLastModified( jarEntry.getTime() );
			
			return targetPath + filename;
			
		} catch (JarException jax) {
			throw new JarException(jax.getMessage()+": "+this.identity+".unpackJarEntry: "+jarFile.getName() + " => " + targetPath + jarEntry.getName());
		} catch (IOException iox) {
			throw new IOException(iox.getMessage()+": "+this.identity+".unpackJarEntry: "+jarFile.getName() + " => " + targetPath + jarEntry.getName());
		} catch (Exception e) {
			throw new Exception(e.getMessage()+": "+this.identity+".unpackJarEntry: "+jarFile.getName() + " => " + targetPath + jarEntry.getName());
		}
	}
	
	private void createManifestFile(ArrayList iSecName, ArrayList iSecOrigin, ArrayList iSecDate, String targetPath) throws IOException, Exception {
		
		Manifest manifest = null;
		File file = null;
		
		try {
			
			if (iSecName==null || iSecOrigin==null || iSecDate==null)
				throw new Exception("iSec is null");
			if ( !((iSecName.size()==iSecOrigin.size()) && (iSecName.size()==iSecDate.size())) )
				throw new Exception("iSec ArrayList sizes are inconsistent "+iSecName.size()+","+iSecOrigin.size()+","+iSecDate.size());
			
		// Falls keine Klasse entpackt wurde, soll auch kein Manifest geschrieben werden
			if ( iSecName.size()==0 ) return;
			
			if ( targetPath==null ) targetPath = "";
			
			file = new File(targetPath + "META-INF");
			if ( !file.isDirectory() )
				file.mkdir();
			
			file = new File(targetPath + "META-INF/MANIFEST.MF");
			file.createNewFile();
			
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("Manifest-Version: 1.0\n");
			sbuf.append("Created-By: "+System.getProperty("java.vm.version")+" ("+System.getProperty("java.vm.vendor")+")\n");
			sbuf.append("\n");
			
			for (int i=0; i<iSecName.size(); i++) {
				sbuf.append("Name: "+ (String)iSecName.get(i) +"\n");
				sbuf.append("Origin: "+ (String)iSecOrigin.get(i) +"\n");
				sbuf.append("Archive-Date: "+ (String)iSecDate.get(i) +"\n");
				sbuf.append("\n");
			}
			
			// Convert the string to a input stream
			InputStream is = new ByteArrayInputStream(sbuf.toString().getBytes("UTF-8"));
			
			// Create the manifest
			manifest = new Manifest(is);
			manifest.write(new FileOutputStream(file));
			
			this.logln("\n manifest file "+ file.getCanonicalPath() +" created");
			
		} catch (IOException iox) {
			throw new IOException(this.identity+"constructManifest: "+iox.getMessage());
		} catch (Exception e) {
			throw new Exception(this.identity+"constructManifest: "+e.getMessage());
		}
	}
	
	private Manifest readManifestFile(String path) throws IOException {
		
		Manifest manifest = null;
		
		if ( path==null ) return null;
		
		File file = new File(path);
		
		if ( file.canRead() ) {
			
			try {
				manifest = new Manifest(new FileInputStream(file));
			} catch (FileNotFoundException nfx) {
			} catch (IOException iox) {
				throw new IOException(this.identity+"readManifestFile: "+file.getCanonicalPath()+": "+iox.getMessage());
			}
		}
		
		return manifest;
	}
	
	private void executeUnzip() throws Exception {
		
		File file = null;
		JarFile jar;
		Enumeration content;
		JarEntry entry;
		String classFile = "";
		String filename = "";
		long archiveDate;
		long hdDate;
		int extracted = 0;
		int processed = 0;
		
		// individuelle Manifest Sektionen
		ArrayList iSecName = new ArrayList();
		ArrayList iSecOrigin = new ArrayList();
		ArrayList iSecDate = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String jarDate;
		int index;
		
		// Verzeichnis der Archive einlesen
		file = new File( this.source );
		
		// alle zu durchsuchenden jar- und zip-Archive ermitteln
		String path = null;
		String[] list = null;
		
		if ( file.isDirectory() ) {
			path = this.source;
			list = this.archiveFilter(file.list());
		} else {
			path = file.getPath().substring(0,file.getPath().length()-file.getName().length());
			list = new String[1];
			list[0] = file.getName();
		}
		
		if ( list.length == 0 ) {
			this.logln("\n no jar/zip archives found");
			return;
		}
		
		if ( this.oldOverwrites )
			this.logln("\n directive: overwrite newer versions");
		else
			this.logln("\n directive: overwrite older versions");
		
		try {
			
			// alle Archive durchgehen
			for (int i=0; i<list.length; i++) {
				
				this.logln("\n unpack "+ list[i] +":");
				
				jar = new JarFile(path + list[i]);
				content = jar.entries();
				
				file = new File(path + list[i]);
				jarDate = ( file.lastModified() < 0 ) ? "unknown" : sdf.format(new Date(file.lastModified()));  
				
				// alle Klassen eines Archivs durchgehen
				while ( content.hasMoreElements() ) {
					
					entry = (JarEntry)content.nextElement();
					classFile = entry.getName();
					
					if ( !(classFile.charAt(classFile.length()-1)=='/' || classFile.toUpperCase().equals("META-INF/MANIFEST.MF")) ) {
						
						index = classFile.lastIndexOf('/')+1; // behandelt R�ckgabewert -1 implizit mit
						// Dateiname muss vom regul�ren Ausdruck erfasst werden
						if ( this.fileFilterPattern.matcher(classFile.substring(index)).matches() ) {
							
							// Paketstruktur aufl�sen?
							if ( this.detach )
								filename = classFile.substring(index);
							else
								filename = classFile;
							
							file = new File( ((this.targetPath!=null) ? this.targetPath : "") + filename );
							
							// Datei existiert schon auf Platte
							if ( file.canRead() ) {
								
								hdDate = file.lastModified();
								archiveDate = entry.getTime();
								
								if ( (!this.oldOverwrites && (archiveDate > hdDate)) || (this.oldOverwrites && (archiveDate < hdDate)) ) {
									this.unpackJarEntry(jar, entry, this.targetPath);
									
									// f�r Manifest individuelle Sektionen
									if ( iSecName.contains(filename) ) {
										index = iSecName.indexOf(filename);
										
										iSecName.remove(index);
										iSecOrigin.remove(index);
										iSecDate.remove(index);
										
										iSecName.add(index, filename);
										iSecOrigin.add(index, list[i]);
										iSecDate.add(index, jarDate);
										
									} else {
										iSecName.add(filename);
										iSecOrigin.add(list[i]);
										iSecDate.add(jarDate);
									}
									
									this.logln("  extract and overwrite "+filename);
									extracted++;
								} else {
									this.logln("  do not overwrite      "+filename);
									processed++;
								}
								
							// Datei existiert noch nicht auf Platte
							} else {
								
								this.unpackJarEntry(jar, entry, this.targetPath);
								
								this.logln("  extract and write     "+filename);
								iSecName.add(filename);
								iSecOrigin.add(list[i]);
								iSecDate.add(jarDate);
								extracted++;
							}
						}
					}
				}
			}
			
			this.createManifestFile(iSecName, iSecOrigin, iSecDate, this.targetPath);
			
			processed += extracted;
			
			this.logln("\n "+ list.length +" archive"+ ((list.length != 1) ? "s" : "") +" passed");
			this.logln(" "+ processed +" file"+ ((processed != 1) ? "s" : "") +" passed");
			this.logln(" "+ extracted +" file"+ ((extracted != 1) ? "s" : "") +" extracted");
			
		} catch (Exception e) {
			this.loglnError("unzip error: "+ e.getMessage() +": "+ classFile);
		}
	}	
	
	private void createJarArchive(File jarFile, File[] tobeJared, String manifestFile) throws IOException, Exception {
		
		if ( jarFile==null ) throw new Exception("jarFile is null");
		if ( tobeJared==null ) throw new Exception("array of files is null");
		if ( tobeJared.length==0 ) throw new Exception("no files to archive");
		
		int BUFFER_SIZE = 10240;
		String path;
		
		try {
			
			byte buffer[] = new byte[BUFFER_SIZE];
			
			// Archiv �ffnen
			FileOutputStream stream = new FileOutputStream(jarFile);
			
			Manifest manifest = readManifestFile(manifestFile);
			
			if ( manifest!=null ) {
				File file = new File(manifestFile);
				this.logln("\n adding existing manifest file "+file.getCanonicalPath());
			
			} else {
				this.logln("\n create new manifest");
				StringBuffer sbuf = new StringBuffer();
				sbuf.append("Manifest-Version: 1.0\n");
				sbuf.append("Created-By: "+System.getProperty("java.vm.version")+" ("+System.getProperty("java.vm.vendor")+")\n");
				sbuf.append("\n");
				
				// Den String in InputStream �berf�hren
				InputStream is = new ByteArrayInputStream(sbuf.toString().getBytes("UTF-8"));
				
				// Manifest erzeugen
				manifest = new Manifest(is);
			}
			
			JarOutputStream out = new JarOutputStream(stream, manifest);
			
			if ( this.compression != -1)
				out.setLevel(this.compression);
			
			JarEntry jarAdd;
			
			for (int i = 0; i < tobeJared.length; i++) {
				
				if ( tobeJared[i] == null )
					continue;
				if ( !tobeJared[i].exists() || tobeJared[i].isDirectory() )
					continue;
				
				// Zielpfad der Datei auf die package-Struktur reduzieren
				path = tobeJared[i].getPath().substring(this.source.length());
				path = path.replaceAll("\\\\", "/");
				
				this.log("\n adding " + path);
				
				// Add archive entry
				jarAdd = new JarEntry(path);
				jarAdd.setTime(tobeJared[i].lastModified());
				out.putNextEntry(jarAdd);
				
				// Klasse ins Archiv schreiben
				FileInputStream in = new FileInputStream(tobeJared[i]);
				
				while (true) {
					
					int nRead = in.read(buffer, 0, buffer.length);
					if ( nRead <= 0 )
						break;
					
					out.write(buffer, 0, nRead);
				}
				
				in.close();
			}
			
			out.close();
			stream.close();
			
			this.logln("\n "+tobeJared.length+" file"+ ((tobeJared.length != 1) ? "s" : "") +" added");
			
		} catch (IOException iox) {
			throw new IOException(iox.getMessage()+": "+this.identity+".createJarArchive: "+jarFile.getAbsolutePath());
		
		} catch (Exception e) {
			throw new Exception(e.getMessage()+": "+this.identity+".createJarArchive: "+jarFile.getAbsolutePath());
		}
	}
	
	private void addFilesToExistingJar(File jarFile, File[] tobeJared, String manifestFile) throws IOException, Exception {
		
		if ( jarFile==null ) throw new Exception("jarFile is null");
		if ( tobeJared==null ) throw new Exception("array of files is null");
		if ( tobeJared.length==0 ) throw new Exception("no files to archive");
		
	// Inhalt des bestehenden jars auslesen
		JarFile jar = new JarFile(jarFile);
		Enumeration jarEnum = jar.entries();
		Hashtable jarContent = new Hashtable();
		
		String elem;
		// alle Dateien eines Archivs durchgehen
		while ( jarEnum.hasMoreElements() ) {
			elem = ((JarEntry)jarEnum.nextElement()).getName();
			jarContent.put(elem, "notnull");
		}
		
		// JarFile schliessen
		jar.close();
		
	// Manifest ermitteln
		Manifest manifest = readManifestFile(manifestFile);
		if ( manifest!=null ) {
			File file = new File(manifestFile);
			this.logln("\n adding existing manifest file "+file.getCanonicalPath());
		
		} else {
			this.logln("\n create new manifest");
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("Manifest-Version: 1.0\n");
			sbuf.append("Created-By: "+System.getProperty("java.vm.version")+" ("+System.getProperty("java.vm.vendor")+")\n");
			sbuf.append("\n");
			
			// Den String in InputStream �berf�hren
			InputStream is = new ByteArrayInputStream(sbuf.toString().getBytes("UTF-8"));
			
			// Manifest erzeugen
			manifest = new Manifest(is);
		}
		
		// Zufallszahl als Teil des Namens f�r die tempor�re Datei
		String rand1 = String.valueOf(Math.random()).substring(2);
		String rand2 = String.valueOf(Math.random()).substring(2);
		String randomName = jarFile.getName()+".tmp."+rand1+rand2;
		
		// Namen f�r tempor�re Datei setzen
		String tempfile = jarFile.getCanonicalPath(); 
		tempfile = tempfile.substring(0,tempfile.length()-jarFile.getName().length())+randomName;
		
		JarInputStream jarIn = new JarInputStream(new FileInputStream(jarFile.getPath()));
		JarOutputStream jarOut = new JarOutputStream(new FileOutputStream( tempfile ), manifest);
		
		if ( this.compression != -1)
			jarOut.setLevel(this.compression);
		
		// We must write every entry from the input JAR to the output JAR, so iterate over the entries:
		// Create a read buffer to transfer data from the input
		
		byte[] buffer = new byte[4096];
		JarEntry entry;
		String path;
		Hashtable inventory = new Hashtable();
		
		int nrOfWrittenFiles = 0;
		
		// add the new files
		for (int i = 0; i < tobeJared.length; i++) {
			
			if ( tobeJared[i] == null )
				continue;
			if ( !tobeJared[i].exists() || tobeJared[i].isDirectory() )
				continue;
			
			// Zielpfad der Datei auf die package-Struktur reduzieren
			path = tobeJared[i].getPath().substring(this.source.length());
			path = path.replaceAll("\\\\", "/");
			
			// Datei ist im Original schon enthalten?
			if ( jarContent.get(path)!=null )
				this.log("\n overwriting " + path);
			else
				this.log("\n      adding " + path);
			
			// Add archive entry
			entry = new JarEntry(path);
			entry.setTime(tobeJared[i].lastModified());
			jarOut.putNextEntry(entry);
			
			// Klasse ins Archiv schreiben
			FileInputStream in = new FileInputStream(tobeJared[i]);
			
			while ( true ) {
				
				int nRead = in.read(buffer, 0, buffer.length);
				if ( nRead <= 0 )
					break;
				
				jarOut.write(buffer, 0, nRead);
			}
			
			inventory.put(entry.getName(), "notnull");
			
			in.close();
			nrOfWrittenFiles++;
		}
		
		// Iterate the entries of the original file
		while ((entry = jarIn.getNextJarEntry()) != null) {
			
			// Exclude the manifest file from the old JAR
			if ("META-INF/MANIFEST.MF".equals(entry.getName())) continue;
			
			// Datei befindet sich bereits im jar
			if ( inventory.get(entry.getName())!=null )
				continue;
			
			// Write the entry to the output JAR
			jarOut.putNextEntry(entry);
			int read;
			while ((read = jarIn.read(buffer)) != -1) {
				jarOut.write(buffer, 0, read);
			}
			
			jarOut.closeEntry();
		}
		
		// Flush and close all the streams
		jarOut.flush();
		jarOut.close();
		jarIn.close();
		
	// altes jar l�schen und neue Datei umbenennen
		File oldJar = new File(this.targetPath);
		try {
			if ( !jarFile.delete() )
				throw new Exception("cannot delete old "+oldJar.getCanonicalPath());
			
			File newJar = new File(tempfile);
			
			if ( !newJar.renameTo(oldJar) )
				throw new Exception("cannot rename tempfile "+tempfile+" to "+oldJar.getCanonicalPath());
			
		} catch (Exception e) {
			throw new Exception("cannot update jar: "+e.getMessage());
		}
		
		this.logln("\n "+nrOfWrittenFiles+" file"+ ((tobeJared.length != 1) ? "s" : "") +" added");
	}
	
	private ArrayList getAllFiles(File file, ArrayList allFiles) {
		
		if ( allFiles==null ) allFiles = new ArrayList();
		
		if ( file.isDirectory() ) {
			
			String[] list = file.list();
			
			for (int i=0; i<list.length; i++)
				this.getAllFiles(new File(file.getPath()+"/"+list[i]), allFiles);
			
		} else {
			allFiles.add(file);
		}
		
		return allFiles;
	}
	
	private void executeZip() throws Exception {
		
		File file = null;
		ArrayList classFiles;
		
		try {
			
		// jar-Archiv �berpr�fen
			file = new File(this.targetPath);
			
			if ( file.isFile() )
				file.delete();
			
			file.createNewFile();
			
		} catch (IOException iox) {
			
			if ( file != null )
				throw new Exception(iox.getMessage()+": "+file.getCanonicalPath());
			else
				throw new Exception(iox.getMessage()+": "+this.targetPath);
		}
		
		File directory = new File(this.source);
		classFiles = new ArrayList();
		classFiles = this.getAllFiles(directory, classFiles);
		
		File[] classes = this.fileFilter((File[])classFiles.toArray(new File[classFiles.size()]), this.fileFilterPattern);
		
		if ( classes!=null && classes.length>0 ) {
			
		// MANIFESTFILE ist gesetzt
			if ( this.manifestFile!=null )
				this.createJarArchive(file, classes, this.manifestFile);
			
		// -m ist gesetzt und MANIFESTFILE ist nicht gesetzt
			else if ( this.standardManifestToCreate )
				this.createJarArchive(file, classes, null);
			
		// default: weder -m noch MANIFESTFILE ist gesetzt
			else
				this.createJarArchive(file, classes, this.source+"META-INF/MANIFEST.MF");
			
			this.logln("\n "+file.getCanonicalPath()+" created");
			
		} else {
			this.logln("\n no files to archive");
		}
	}
	
	// Falls das Zieljar nicht existiert, wie zip
	// ansonsten ausgew�hlte Dateien zu jar hinzuf�gen
	private void executeAdd() throws Exception {
		
		File file = null;
		ArrayList content;
		
		// jar-Archiv �berpr�fen
		file = new File(this.targetPath);
		
		// wenn Datei noch nicht exisitert => executeZip() ausf�hren
		if ( !file.exists() ) {
			this.executeZip();
			return;
			
		} else if ( !file.canWrite() ) {
			try {
				throw new Exception("cannot write "+file.getCanonicalPath());
			} catch (Exception e) {
				throw new Exception("cannot write "+this.targetPath);
			}
		}
		
	// ausgew�hlte Dateien zu bestehendem jar hinzuf�gen
		
		File directory = new File(this.source);
		content = new ArrayList();
		content = this.getAllFiles(directory, content);
		
		File[] filesToAdd = this.fileFilter((File[])content.toArray(new File[content.size()]), this.fileFilterPattern);
		
		if ( filesToAdd!=null && filesToAdd.length>0 ) {
			
			// MANIFESTFILE ist gesetzt
			if ( this.manifestFile!=null )
				this.addFilesToExistingJar(file, filesToAdd, this.manifestFile);
			
			// -m ist gesetzt und MANIFESTFILE ist nicht gesetzt
			else if ( this.standardManifestToCreate )
				this.addFilesToExistingJar(file, filesToAdd, null);
			
			// default: weder -m noch MANIFESTFILE ist gesetzt
			else
				this.addFilesToExistingJar(file, filesToAdd, this.source+"META-INF/MANIFEST.MF");
			
			this.logln("\n "+file.getCanonicalPath()+" updated");
			
		} else {
			this.logln("\n no files to archive");
		}
	}
	
	private void execute() {
		
	// Pfad- und Dateiangaben auf Korrektheit �berpr�fen
		try {
			
			File file = new File(this.source);
			
			if ( !file.canRead() )
				throw new Exception(this.source +" not found or not readable");
			
			if ( this.check || this.zip || this.add) {
				if ( !file.isDirectory() )
					throw new Exception(file.getCanonicalPath() +" is no directory");
					
			} else if ( this.unzip ) {
				if ( !file.isDirectory() ) {
					if ( !file.isFile() ) {
						throw new Exception(this.source +" is no normal file");
					} else {
						if ( this.source.length()<5 )
							throw new Exception(file.getCanonicalPath() +" is no valid archiv");
						else {
							String suffix = this.source.substring(this.source.length()-4);
							if ( !(suffix.equals(".jar") || suffix.equals(".zip")) )
								throw new Exception(file.getCanonicalPath() +" is no valid archiv");
						}
					}
				}
			}
			
		// Quellpfad normalisieren
			if ( file.isDirectory() ) {
				this.source = this.source.replaceAll("\\\\", "/");
				if ( !(this.source.charAt(this.source.length()-1)=='/') )
					this.source += "/";
			}
			
		// zus�tzlich f�r unzip optionales Zielverzeichnis testen
			if ( this.unzip ) {
				if ( this.targetPath!=null ) {
					try {
						
						file = new File(this.targetPath);
						
						if ( !file.isDirectory() ) {
							throw new Exception(this.targetPath +" is no directory");
						} else {
							if ( !file.canWrite() )
								throw new Exception(this.targetPath +" is not writable");
						}
						
					// Zielpfad normalisieren
						this.targetPath = this.targetPath.replaceAll("\\\\", "/");
						if ( !(this.targetPath.charAt(this.targetPath.length()-1)=='/') )
							this.targetPath += "/";
						
					} catch (IOException iox) {
						
						if ( file!=null ) {
							if ( file.isFile() )
								throw new Exception(file.getCanonicalPath() +": "+ iox.getMessage());
							else
								throw new Exception(this.targetPath +": "+ iox.getMessage());
						} else
							throw new Exception(this.targetPath +": "+ iox.getMessage());
					}
				}
			}
			
		// zus�tzlich f�r zip Manifest und Ziel-Archiv testen
			if ( this.zip || this.add ) {
				
				// Manifest �berpr�fen
				if ( this.manifestFile!=null ) {
					file = new File(this.manifestFile);
					if ( !file.canRead() )
						throw new Exception("manifest file "+file.getCanonicalPath()+" not found or not readable");
				}
				
				// jar-Archiv �berpr�fen
				file = new File(this.targetPath);
				
				if ( file.isDirectory() )
					throw new Exception( file.getCanonicalPath() + " is a directory");
			}
			
		} catch (Exception e) {
			this.loglnError("\n abort: "+e.getMessage());
			System.exit(-1);
		}
		
	// spezifizierte Aufgabe ausf�hren
		try {
			
			if ( this.check ) this.executeCheck(); else
			if ( this.unzip ) this.executeUnzip(); else
			if ( this.zip   ) this.executeZip(); else
			if ( this.add   ) this.executeAdd();
			
		} catch (Exception e) {
			this.loglnError("\n error: "+e.getMessage());
			System.exit(-1);
		}
		
		System.exit(0);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JarMerge jarmerge = new JarMerge(args);
		jarmerge.execute();
	}
}
