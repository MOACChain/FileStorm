package main

import (
	"errors"
	"net/http"
	"net/http/httptest"
	"testing"

	. "github.com/prashantv/gostub"
	. "github.com/smartystreets/goconvey/convey"
)

func TestStormCatcherEndpoint(t *testing.T) {
	Convey("Test Ipfs read endpoint", t, func() {
		Convey("Handle normal case, return 200", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/read?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, nil)
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(readHandler)

			// Our handlers satisfy http.Handler, so we can call their ServeHTTP method
			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 200, ShouldBeTrue)
			So(recorder.Body.String() == "", ShouldBeTrue)
		})

		Convey("Handle error case, return 500", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/read?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, errors.New(""))
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(readHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 500, ShouldBeTrue)
			So(recorder.Body.String() == "Can not enqueue read request.\n", ShouldBeTrue)
		})
	})

	Convey("Test Ipfs write endpoint", t, func() {
		Convey("Handle normal case, return 200", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/write?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, nil)
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(readHandler)

			// Our handlers satisfy http.Handler, so we can call their ServeHTTP method
			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 200, ShouldBeTrue)
			So(recorder.Body.String() == "", ShouldBeTrue)
		})

		Convey("Handle error case, return 500", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/write?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, errors.New(""))
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(writeHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 500, ShouldBeTrue)
			So(recorder.Body.String() == "Can not enqueue write request.\n", ShouldBeTrue)
		})
	})

	Convey("Test Ipfs delete endpoint", t, func() {
		Convey("Handle normal case, return 200", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/delete?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, nil)
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(deleteHandler)

			// Our handlers satisfy http.Handler, so we can call their ServeHTTP method
			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 200, ShouldBeTrue)
			So(recorder.Body.String() == "", ShouldBeTrue)
		})

		Convey("Handle error case, return 500", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/delete?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub AddTaskToQueue
			stubs := StubFunc(&AddTaskToQueue, errors.New(""))
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(deleteHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 500, ShouldBeTrue)
			So(recorder.Body.String() == "Can not enqueue delete request.\n", ShouldBeTrue)
		})
	})

	Convey("Test Ipfs checkhash endpoint", t, func() {
		Convey("Handle normal case, return 200", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/verify?file_hash=QmTor1GsqZQwJdFoTYjAdEEjXDZgYDm1oc3Lj8waHUKRFN&offset=100", nil)
			if err != nil {
				t.Fatal(err)
			}

			// stub HandleIPFSVerify
			stubs := StubFunc(&HandleIPFSVerify, nil, []byte{'h', 'e', 'l', 'l', 'o'})
			defer stubs.Reset()

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(verifyHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 200, ShouldBeTrue)
			So(recorder.Body.String() == "hello", ShouldBeTrue)

		})

		Convey("Handle normal case, return 400, no file hash", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/verify?offset=100", nil)
			if err != nil {
				t.Fatal(err)
			}

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(verifyHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 400, ShouldBeTrue)
			So(recorder.Body.String() == "Invalid verify parameter: Filehash: invalid length\n", ShouldBeTrue)
		})

		Convey("Handle normal case, return 400, empty file hash", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/verify?file_hash=&offset=100", nil)
			if err != nil {
				t.Fatal(err)
			}

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(verifyHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 400, ShouldBeTrue)
			So(recorder.Body.String() == "Invalid verify parameter: Filehash: invalid length\n", ShouldBeTrue)
		})

		Convey("Handle normal case, return 400, no offset", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/verify?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1", nil)
			if err != nil {
				t.Fatal(err)
			}

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(verifyHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 400, ShouldBeTrue)
			So(recorder.Body.String() == "Can not parse offset value.\n", ShouldBeTrue)
		})

		Convey("Handle normal case, return 400, invalid offset", func() {
			// Create a request to pass to our handler.
			req, err := http.NewRequest("GET", "/ipfs/verify?file_hash=QmW8ubjTcjVz2VKn497bEZ5wQaLPS6chLU5DaQSq3NWMa1&offset=abc", nil)
			if err != nil {
				t.Fatal(err)
			}

			// We create a ResponseRecorder (which satisfies http.ResponseWriter) to record the response.
			recorder := httptest.NewRecorder()
			handler := http.HandlerFunc(verifyHandler)

			// directly and pass in our Request and ResponseRecorder.
			handler.ServeHTTP(recorder, req)

			// Check the status code is what we expect.
			So(recorder.Code == 400, ShouldBeTrue)
			So(recorder.Body.String() == "Invalid verify parameter: Offset: regular expression mismatch\n", ShouldBeTrue)
		})
	})
}
