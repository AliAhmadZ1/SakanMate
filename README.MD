# SakanMate - Apartment Rental Platform API


This project is part of the **Web Development Program Using Java and SpringBoot** presented by **Tuwaiq Academy**.

## Table of Contents
- [Models](#models)
- [Controllers](#controllers)
  - [Admin](#admin-controller)
  - [Apartment](#apartment-controller)
  - [ApartmentReview](#apartmentreview-controller)
  - [Complaint](#complaint-controller)
  - [Contract](#contract-controller)
  - [EmailNotification](#emailnotification-controller)
  - [Owner](#owner-controller)
  - [Post](#post-controller)
  - [Renter](#renter-controller)
  - [Request](#request-controller)
- [Services](#services)
- [Contact Information](#contact)

## Models

### Admin
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| name | String | Not empty | Admin name |
| email | String | Email format | Admin email |
| password | String | Not empty | Admin password |

### Apartment
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| title | String | Not empty | Apartment title |
| description | String | | Apartment description |
| number_of_remaining | Integer | | Available units |
| max_renters | Integer | | Maximum capacity |
| availability | Boolean | Default false | Availability status |
| document_number | String | | Document reference |
| monthlyPrice | Double | @Positive | Monthly rental price |
| isApproved | Boolean | Default false | Approval status |
| rejectionReason | String | | Rejection reason |

### ApartmentReview
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| rating | Integer | @Min(1), @Max(5) | Rating (1-5 stars) |
| description | String | | Review text |
| up_vote | Integer | Default 0 | Upvotes count |
| down_vote | Integer | Default 0 | Downvotes count |

### Complaint
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| title | String | Not empty | Complaint title |
| description | String | | Complaint details |

### Contract
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| totalPrice | Double | @Positive | Total contract value |
| startDate | LocalDateTime | @FutureOrPresent | Contract start date |
| endDate | LocalDateTime | @Future | Contract end date |
| isRenewed | Boolean | Default false | Renewal status |
| ownerApproved | Boolean | Default false | Owner approval |
| renterAccepted | Boolean | Default false | Renter acceptance |

### Owner
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| name | String | Not empty | Owner name |
| email | String | Email format | Owner email |
| password | String | Not empty | Owner password |
| isApproved | Boolean | Default false | Approval status |
| rejectionReason | String | | Rejection reason |
| licenseNumber | String | | Business license |

### Post
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| status | String | Pattern: "approved\|pending\|canceled\|rented" | Listing status |
| postDate | LocalDate | | Creation date |
| numberOfApprovedRequests | Integer | | Approved requests count |
| approved | Boolean | Default false | Admin approval |
| ApprovedDate | LocalDateTime | | Approval timestamp |
| rejectionReason | String | | Rejection reason |

### Renter
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| name | String | @NotEmpty | Renter name |
| email | String | @Email | Renter email |
| password | String | @NotEmpty | Renter password |

### Request
| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Integer | Auto-generated | Unique identifier |
| state | String | Pattern: "accepted\|rejected\|pending\|canceled" | Request status |
| requestDate | LocalDateTime | | Creation timestamp |
| months | Integer | @NotNull | Rental duration |

## Controllers

### Admin Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/add` | POST | Admin object | Register new admin |
| `/get/{id}` | GET | adminId | Get admin details |
| `/update/{id}` | PUT | adminId, Admin object | Update admin info |
| `/delete/{id}` | DELETE | adminId | Remove admin |

### Apartment Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get` | GET | None | Get all apartments |
| `/update/{id}` | PUT | apartmentId, Apartment object | Update apartment |
| `/delete/{id}` | DELETE | apartmentId | Remove apartment |
| `/approve/{id}/{adminId}` | PUT | apartmentId, adminId | Approve apartment |
| `/reject-apartment/{id}/{adminId}` | PUT | apartmentId, reason, adminId | Reject apartment |
| `/add-apartment-to-sakanmate/{id}` | POST | ownerId, Apartment object | Add new apartment |

### ApartmentReview Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get` | GET | None | Get all reviews |
| `/add` | POST | ReviewDTO | Submit new review |
| `/update/{id}` | PUT | reviewId, ReviewDTO | Update review |
| `/delete/{id}` | DELETE | reviewId | Remove review |

### Complaint Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get` | GET | None | Get all complaints |
| `/update/{id}` | PUT | complaintId, ComplaintDTO | Update complaint |
| `/delete/{id}` | DELETE | complaintId | Remove complaint |
| `/file-complaint/{renterId}/{apartmentId}` | POST | renterId, apartmentId, title, description | File new complaint |
| `/assign-complaint-to-admin/{adminId}/{complaintId}` | PUT | adminId, complaintId | Assign complaint to admin |

### Contract Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get-all-contracts` | GET | None | List all contracts |
| `/update-contract/{contractId}` | PUT | contractId, Contract object | Update contract |
| `/delete-contract/{contractId}` | DELETE | contractId | Remove contract |
| `/get-contract-as-pdf/{contractId}` | GET | contractId | Download contract PDF |
| `/is-expired/{id}` | GET | contractId | Check expiration |
| `/{oldId}/renew` | POST | oldContractId, months | Request renewal |
| `/{id}/{ownerId}approve-renewal` | PUT | contractId, ownerId | Approve renewal |
| `/accept-contract/{renterId}/{contractId}/{requestId}` | PUT | renterId, contractId, requestId | Accept contract |
| `/create-contract/{adminId}/{requestId}` | POST | adminId, requestId | Create new contract |
| `/approve-by-owner/{id}/{ownerId}` | PUT | contractId, ownerId | Owner approval |

### EmailNotification Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/send` | POST | EmailRequest | Send email notification |

### Owner Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get` | GET | None | Get all owners |
| `/add` | POST | Owner object | Register owner |
| `/update/{id}` | PUT | ownerId, Owner object | Update owner |
| `/delete/{id}` | DELETE | ownerId | Remove owner |
| `/owners/{id}/approve/{adminId}` | PUT | ownerId, adminId | Approve owner |
| `/reject-by-admin/{id}/{adminId}` | PUT | ownerId, reason, adminId | Reject owner |
| `/disable-owner/admin/{admin_id}/owner/{owner_id}` | PUT | adminId, ownerId | Disable owner |

### Post Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get` | GET | None | Get all posts |
| `/update/{id}` | PUT | postId, PostDTO | Update post |
| `/delete/{id}` | DELETE | postId | Remove post |
| `/posts/{postId}/approve/{adminId}` | PUT | postId, adminId | Approve post |
| `/cancel-posts/{id}/{ownerId}` | PUT | postId, ownerId | Cancel post |
| `/posts/{id}/{adminId}` | PUT | postId, reason, adminId | Reject post |
| `/create-post/{id}/apartment/{apartment_id}` | POST | ownerId, apartmentId | Create new post |

### Renter Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/get-all-renters` | GET | None | List all renters |
| `/add-renter` | POST | Renter object | Register renter |
| `/update-renter/{renterId}` | PUT | renterId, Renter object | Update renter |
| `/delete-renter/{renterId}` | DELETE | renterId | Remove renter |
| `/make-review/{id}/apartment/{apartment_id}` | POST | renterId, apartmentId, Review | Submit review |

### Request Controller
| Endpoint | Method | Parameters | Description |
|----------|--------|------------|-------------|
| `/request-apartment/{renterId}/{postId}` | POST | renterId, postId, months | Create request |
| `/check-request-status/{renterId}/{requestId}` | GET | renterId, requestId | Check status |
| `/cancel-request/{renterId}/{requestId}` | PUT | renterId, requestId | Cancel request |
| `/accept-request/{ownerId}/{requestId}` | PUT | ownerId, requestId | Accept request |
| `/reject-request/{ownerId}/{requestId}` | PUT | ownerId, requestId | Reject request |
| `/get-owner-pending-requests/{ownerId}` | GET | ownerId | List pending requests |

## Services

### Key Service Methods

**AdminService**
- `addAdmin()`: Registers new admin
- `updateAdmin()`: Modifies admin details
- `approveOwner()`: Approves owner registration

**ApartmentService**
- `approveApartment()`: Approves apartment listing
- `rejectApartment()`: Rejects apartment with reason
- `addApartmentToSakanMate()`: Creates new apartment

**ContractService**
- `createContract()`: Generates new rental contract
- `getContractAsPdf()`: Exports contract as PDF
- `requestRenewal()`: Handles contract renewal

**OwnerService**
- `approveOwner()`: Approves owner account
- `disableOwner()`: Deactivates owner account
- `calculateAverageRatingByOwner()`: Computes owner rating

**RequestService**
- `requestApartment()`: Creates rental request
- `acceptRequest()`: Owner accepts request
- `getOwnerPendingRequests()`: Lists pending requests

## Contact Information
For any questions, feedback, or collaboration opportunities, feel free to reach out:

- **Email**: Ayman.f.alharbi@gmail.com
- **Email**: ali.alshehri@gmail.com
- **Email**: khadija95xx@gmail.com
