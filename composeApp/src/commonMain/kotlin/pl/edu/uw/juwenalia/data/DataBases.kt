package pl.edu.uw.juwenalia.data

import io.github.vinceglb.filekit.core.PlatformFile
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class TicketDataBase {

    val realm: Realm by lazy {
        val configuration = RealmConfiguration.create(schema = setOf(FileData::class))
        Realm.open(configuration)
    }

    fun getFileData(filePath : String): FileData? {
        val querySingleFile = realm.query<FileData>("filePath == $filePath").first()
        return querySingleFile.find()
    }

    fun getAllFilesData(): List<FileData> = realm.query<FileData>().find()

    fun addFileData(fileData : PlatformFile) {
        if (fileData.path == null) return
        if (fileData.path?.let { getFileData(it) } == null) return

        realm.writeBlocking {
            copyToRealm(FileData().apply {
                filePath = fileData.path
                platformFile = fileData
            })
        }
    }

    fun deleteFileData(filePath : String) {
        val fileToDelete = getFileData(filePath)
        realm.writeBlocking {
            if (fileToDelete != null) {
                findLatest(fileToDelete)
                    ?.also { delete(it) }
            }
        }
    }
}