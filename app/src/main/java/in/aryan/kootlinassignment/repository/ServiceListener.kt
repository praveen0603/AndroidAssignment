package `in`.aryan.kootlinassignment.repository

/**
 * it contain the common method  @getServerResponse ,getError for handling the api response of api.
 */
interface ServiceListener<T> {
    abstract fun getServerResponse(response: T, requestcode: Int)
    abstract fun getError(error: ErrorModel, requestcode: Int)
}