package com.manudev.data.response

/**
 * A sealed class representing the status of an API response.
 * It can have three possible states: Success, Loading, or Error.
 *
 * @param T The type of data associated with the API response.
 */
sealed class APIResponseStatus<T> {
    /**
     * Represents a successful API response with the associated [data].
     *
     * @param data The data received from the API call.
     */
    class Success<T>(val data: T) : APIResponseStatus<T>()

    /**
     * Represents an error state of the API call with the associated [message].
     *
     * @param message The error message describing the reason for the failure.
     */
    class Error<T>(val message: String) : APIResponseStatus<T>()
}
