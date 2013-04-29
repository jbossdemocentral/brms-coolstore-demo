'*  Script name:   unzip.vbs
'*  Created on:    4/8/2013
'*  Author:        Andrew Block
'*  Purpose:       Extracts the contents of a specified compressed file
'*                 to a specified folder


	Dim pathToZipFile 
	Dim dirToExtractFiles
	
	If WScript.Arguments.Count = 2 Then
		pathToZipFile = WScript.Arguments.Item(0)
		dirToExtractFiles = WScript.Arguments.Item(1)
	Else
		WScript.Echo "2 Parameters are Required"
		WScript.Quit(99)
	End If
		
    Dim fso
    Set fso = CreateObject("Scripting.FileSystemObject")
 
    pathToZipFile = fso.GetAbsolutePathName(pathToZipFile)
    dirToExtractFiles = fso.GetAbsolutePathName(dirToExtractFiles)
 
    If (Not fso.FileExists(pathToZipFile)) Then
        WScript.Echo "Zip file does not exist: " & pathToZipFile
        WScript.Quit(99)
    End If
 
    If Not fso.FolderExists(dirToExtractFiles) Then
        WScript.Echo "Directory does not exist: " & dirToExtractFiles
        WScript.Quit(99)
    End If
 
    dim sa
    set sa = CreateObject("Shell.Application")
 
    Dim zip
    Set zip = sa.NameSpace(pathToZipFile)
 
    Dim d
    Set d = sa.NameSpace(dirToExtractFiles)
 
	'd.CopyHere zip.items, 4
	d.CopyHere zip.items, 20
    
	Do Until zip.Items.Count <= d.Items.Count
        Wscript.Sleep(200)
    Loop